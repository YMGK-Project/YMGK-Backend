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
import proje.v1.api.domian.classroom.Classroom;
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
    private ClassroomService classroomService;
    @Autowired
    private RoleService roleService;
    private String TEACHER = "Teacher";

    @RequestMapping(value = "add/classroom", method = RequestMethod.POST)
    public Response<Classroom> addClassroom(@Valid @RequestBody RequestClassroom requestClassroom, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        roleService.validatePermission(ContextHolder.user, TEACHER);
        Classroom classroom = classroomService.addClassroom(
                requestClassroom.getCod(),
                requestClassroom.getName(),
                requestClassroom.getSectionType(),
                requestClassroom.getEducationType()
        );
        teacherService.saveClassroom(ContextHolder.user, classroom);
        return new Response<>(200, true, classroom);
    }

    @RequestMapping(value = "get/classroom/all", method = RequestMethod.GET)
    public Response<List<Classroom>> getAllClassroom(){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        List<Classroom> classrooms = ContextHolder.user.getTeacher().getClassroomList();
        return new Response<>(200, true, classrooms);
    }
}
