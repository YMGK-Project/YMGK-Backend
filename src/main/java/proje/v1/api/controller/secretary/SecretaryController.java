package proje.v1.api.controller.secretary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proje.v1.api.config.auth.ContextHolder;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.converter.user.UserConverter;
import proje.v1.api.domian.user.Users;
import proje.v1.api.dto.user.UserDTO;
import proje.v1.api.message.student.RequestStudent;
import proje.v1.api.message.teacher.RequestTeacher;
import proje.v1.api.service.user.RoleService;
import proje.v1.api.service.secretary.SecretaryService;
import proje.v1.api.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/secretary")
public class SecretaryController {

    @Autowired
    private SecretaryService secretaryService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserConverter userConverter;
    private String SECRETARY = "Secretary";

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){
        secretaryService.testSecretary();
    }

    @RequestMapping(value = "/add/teacher", method = RequestMethod.POST)
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

    @RequestMapping(value = "/add/student", method = RequestMethod.POST)
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
