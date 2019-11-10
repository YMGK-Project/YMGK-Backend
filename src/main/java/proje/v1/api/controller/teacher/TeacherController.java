package proje.v1.api.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proje.v1.api.config.auth.ContextHolder;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.converter.classroom.ClassroomConverter;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.dto.classroom.ClassroomDTO;
import proje.v1.api.message.classroom.RequestClassroom;
import proje.v1.api.service.classroom.ClassroomService;
import proje.v1.api.service.user.RoleService;
import proje.v1.api.service.teacher.TeacherService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassroomConverter classroomConverter;
    @Autowired
    private RoleService roleService;
    private String TEACHER = "Teacher";

    @RequestMapping(value = "/classrooms", method = RequestMethod.POST)
    public Response<ClassroomDTO> addClassroom(@Valid @RequestBody RequestClassroom requestClassroom, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        roleService.validatePermission(ContextHolder.user, TEACHER);
        Classroom classroom = teacherService.addClassroomAndGet(
                ContextHolder.user,
                requestClassroom.getCod(),
                requestClassroom.getName(),
                requestClassroom.getSectionType(),
                requestClassroom.getEducationType()
                );
        ClassroomDTO classroomDTO = classroomConverter.convert(classroom);
        return new Response<>(201, true, classroomDTO);
    }

    @RequestMapping(value = "/classrooms", method = RequestMethod.GET)
    public Response<List<ClassroomDTO>> getAllClassroom(){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        List<Classroom> classrooms = ContextHolder.user.getTeacher().getClassroomList();
        List<ClassroomDTO> classroomDTOS = classroomConverter.convert(classrooms);
        return new Response<>(200, true, classroomDTOS);
    }
}
