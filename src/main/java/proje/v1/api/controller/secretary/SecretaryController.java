package proje.v1.api.controller.secretary;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import proje.v1.api.config.auth.ContextHolder;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.converter.student.StudentConverter;
import proje.v1.api.converter.teacher.TeacherConverter;
import proje.v1.api.converter.user.UserConverter;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.user.Users;
import proje.v1.api.dto.student.StudentDTO;
import proje.v1.api.dto.teacher.TeacherDTO;
import proje.v1.api.dto.user.UserDTO;
import proje.v1.api.message.student.RequestStudent;
import proje.v1.api.message.teacher.RequestTeacher;
import proje.v1.api.service.student.StudentService;
import proje.v1.api.service.teacher.TeacherService;
import proje.v1.api.service.user.RoleService;
import proje.v1.api.service.secretary.SecretaryService;
import proje.v1.api.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Sekreter İşlemleri")
@RestController
@RequestMapping(value = "/secretaries")
public class SecretaryController {

    @Autowired
    private SecretaryService secretaryService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private TeacherConverter teacherConverter;
    @Autowired
    private StudentConverter studentConverter;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    private String SECRETARY = "Secretary";

    @ApiOperation(value = "Id'ye göre öğretmeni siler")
    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.DELETE)
    public Response<String> deleteTeacher(@PathVariable Long id){
        roleService.validatePermission(ContextHolder.user,SECRETARY);
        teacherService.deleteTeacherById(id);
        return new Response<>(200,true,"Kayıt başarıyla silindi.");
    }

    @ApiOperation(value = "Id'ye göre öğrenciyi siler")
    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    public Response<String> deleteStudent(@PathVariable Long id){
        roleService.validatePermission(ContextHolder.user,SECRETARY);
        studentService.deleteStudentById(id);
        return new Response<>(200,true,"Kayıt başarıyla silindi");
    }

    @ApiOperation(value = "Id'ye göre öğretmeni getirir")
    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.GET)
    public Response<TeacherDTO> getTeacher(@PathVariable Long id){
        roleService.validatePermission(ContextHolder.user,SECRETARY);
        Teacher teacher=teacherService.findById(id);
        TeacherDTO teacherDTO=teacherConverter.convert(teacher);
        return new Response<>(200,true,teacherDTO);
    }

    @ApiOperation(value = "Id'ye göre öğrenciyi getirir")
    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    public Response<StudentDTO> getStudent(@PathVariable Long id){
        roleService.validatePermission(ContextHolder.user,SECRETARY);
        Student student=studentService.findById(id);
        StudentDTO studentDTO=studentConverter.convert(student);
        return new Response<>(200,true,studentDTO);
    }

    @ApiOperation(value = "Sekreterin öğretmen eklemesini sağlar")
    @RequestMapping(value = "/teachers", method = RequestMethod.POST)
    public Response<UserDTO> addTeacher(@Valid @RequestBody RequestTeacher requestTeacher, BindingResult bindingResult) {
        BindingValidator.validate(bindingResult);
        roleService.validatePermission(ContextHolder.user, SECRETARY);
        Users user = secretaryService.saveTeacherAndGet(
                requestTeacher.getEmail(),
                requestTeacher.getUsername(),
                requestTeacher.getPassword(),
                requestTeacher.getName(),
                requestTeacher.getSurname());
        UserDTO userDTO = userConverter.convert(user);
        return new Response<>(201, true, userDTO);
    }

    @ApiOperation(value = "Sekreterin öğrenci eklemesini sağlar")
    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public Response<UserDTO> addStudent(@Valid @RequestBody RequestStudent requestStudent, BindingResult bindingResult) {
        BindingValidator.validate(bindingResult);
        roleService.validatePermission(ContextHolder.user, SECRETARY);
        Users user = secretaryService.saveStudentAndGet(
                requestStudent.getEmail(),
                requestStudent.getUsername(),
                requestStudent.getPassword(),
                requestStudent.getFingerMark(),
                requestStudent.getName(),
                requestStudent.getSurname());
        UserDTO userDTO = userConverter.convert(user);
        return new Response<>(201, true, userDTO);
    }
}
