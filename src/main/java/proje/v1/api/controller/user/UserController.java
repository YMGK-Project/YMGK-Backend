package proje.v1.api.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proje.v1.api.config.auth.ContextHolder;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.domian.user.Users;
import proje.v1.api.message.user.RequestPasswordChange;
import proje.v1.api.service.user.RoleService;
import proje.v1.api.service.user.UserService;

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
