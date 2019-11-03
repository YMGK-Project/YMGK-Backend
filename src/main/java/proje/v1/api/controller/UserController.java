package proje.v1.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proje.v1.api.auth.ContextHolder;
import proje.v1.api.base.messages.Response;
import proje.v1.api.base.util.BindingValidator;
import proje.v1.api.domian.user.Users;
import proje.v1.api.message.RequestPasswordChange;
import proje.v1.api.service.RoleService;
import proje.v1.api.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    public Response<Users> resetPassword(@Valid @RequestBody RequestPasswordChange requestPasswordChange, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        roleService.validateIsUser(ContextHolder.user);
        userService.validateUser(
                ContextHolder.user.getUsername(),
                requestPasswordChange.getOldPassword());
        userService.resetPassword(
                ContextHolder.user.getUsername(),
                requestPasswordChange.getNewPassword());
        Users user = userService.findById(ContextHolder.user.getUsername()).get();
        return new Response<>(200, true, user);
    }
}
