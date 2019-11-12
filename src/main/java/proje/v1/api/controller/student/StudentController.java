package proje.v1.api.controller.student;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proje.v1.api.common.messages.Response;
import proje.v1.api.converter.student.StudentConverter;
import proje.v1.api.dto.classroom.ClassroomDTO;
import proje.v1.api.service.student.StudentService;

import java.util.List;

@Api(description = "Öğrenci İşlemleri")
@RestController
@RequestMapping(value = "/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentConverter studentConverter;

    @RequestMapping(value = "/classrooms", method = RequestMethod.GET)
    public Response<List<ClassroomDTO>> getAllClassroom(){
        return null;
    }

}
