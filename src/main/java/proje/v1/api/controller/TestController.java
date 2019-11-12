package proje.v1.api.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.domian.student.Student;
import proje.v1.api.message.login.RequestLogin;
import proje.v1.api.service.classroom.ClassroomService;
import proje.v1.api.service.secretary.SecretaryService;
import proje.v1.api.service.student.StudentService;
import proje.v1.api.service.teacher.TeacherService;
import proje.v1.api.service.user.UserService;
import java.util.List;

@Api(description = "Test İşlemleri")
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;
    @Autowired
    private SecretaryService secretaryService;
    @Autowired
    private ClassroomService classroomService;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public List<Student> getAllStudent(){
        return studentService.findAll();
    }

    @RequestMapping(value = "/students/{id}/classroom", method = RequestMethod.GET)
    public List<Classroom> getStudentsClassroom(@PathVariable Long id){
        return studentService.findAllClassroomBy(id);
    }

    @RequestMapping(value = "/classrooms", method = RequestMethod.GET)
    public List<Classroom> getAllClassroom(){
        return classroomService.findAll();
    }

    @RequestMapping(value = "/classrooms/{id}/rollcalls", method = RequestMethod.GET)
    public List<RollCall> getClassroomsRollcalls(@PathVariable Long id){
        return classroomService.findAllRollCallsById(id);
    }

}
