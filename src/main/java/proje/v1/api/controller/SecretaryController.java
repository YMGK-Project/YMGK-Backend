package proje.v1.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proje.v1.api.base.messages.Response;
import proje.v1.api.base.util.BindingValidator;
import proje.v1.api.domian.Teacher.Teacher;
import proje.v1.api.domian.user.Users;
import proje.v1.api.message.RequestStudent;
import proje.v1.api.message.RequestTeacher;
import proje.v1.api.service.SecretaryService;

import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

@RestController
@RequestMapping(value = "/secretary")
public class SecretaryController {

    @Autowired
    SecretaryService secretaryService;

    @RequestMapping(value = "/add/teacher", method = RequestMethod.POST)
    public Response<Users> addTeacher(@Valid @RequestBody RequestTeacher requestTeacher, BindingResult bindingResult, Principal userPrincipal){
        BindingValidator.validate(bindingResult);
        secretaryService.checkPermission(userPrincipal);
        Users user = secretaryService.saveTeacher(
                requestTeacher.getUsername(),
                requestTeacher.getPassword(),
                requestTeacher.getName(),
                requestTeacher.getSurname());
        return new Response<>(200, true, user);
    }

    @RequestMapping(value = "/add/student", method = RequestMethod.POST)
    public Response<Users> addStudent(@Valid @RequestBody RequestStudent requestStudent, BindingResult bindingResult, Principal userPrincipal){
        BindingValidator.validate(bindingResult);
        secretaryService.checkPermission(userPrincipal);
        Users user = secretaryService.saveStudent(
                requestStudent.getUsername(),
                requestStudent.getPassword(),
                requestStudent.getFingerMark(),
                requestStudent.getName(),
                requestStudent.getSurname());
        return new Response<>(200, true, user);
    }
}
