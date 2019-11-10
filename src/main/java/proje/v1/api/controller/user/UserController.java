package proje.v1.api.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import proje.v1.api.config.auth.ContextHolder;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.converter.user.UserConverter;
import proje.v1.api.domian.user.TemporaryTokenHolder;
import proje.v1.api.domian.user.Users;
import proje.v1.api.dto.user.UserDTO;
import proje.v1.api.message.user.RequestPasswordChange;
import proje.v1.api.message.user.RequestPasswordForgot;
import proje.v1.api.service.user.RoleService;
import proje.v1.api.service.user.TemporaryTokenHolderService;
import proje.v1.api.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TemporaryTokenHolderService temporaryTokenHolderService;

    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    public Response<UserDTO> changePassword(@Valid @RequestBody RequestPasswordChange requestPasswordChange, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        roleService.validateIsUser(ContextHolder.user);
        Users user = userService.changePasswordAndGetUser(
                ContextHolder.user.getUsername(),
                requestPasswordChange.getOldPassword(),
                requestPasswordChange.getNewPassword());
        UserDTO userDTO = userConverter.convert(user);
        return new Response<>(200, true, userDTO);
    }

    @RequestMapping(value = "/forgot/password", method = RequestMethod.POST)
    public Response<String> forgotPassword(@Valid @RequestBody RequestPasswordForgot requestPasswordForgot, BindingResult bindingResult, HttpServletRequest request){
        BindingValidator.validate(bindingResult);
        userService.forgotPassword(
                requestPasswordForgot.getEmail(),
                request.getScheme(),
                request.getServerName());
        return new Response<>(200, true, "Email send successfully.");
    }

    @RequestMapping(value = "/reset/password/{token}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable String token){
        TemporaryTokenHolder tokenHolder = temporaryTokenHolderService.findById(token);
        return "view.xxx/"+tokenHolder.getToken();
    }
    
    @RequestMapping(value="/reset/password/{token}",method=RequestMethod.POST)
    public Response<UserDTO> resetPasswordSuccess(@Valid @RequestBody RequestPasswordChange requestPasswordChange,
                                                 @PathVariable String token,
                                                 BindingResult bindingResult) {
        BindingValidator.validate(bindingResult);
        Users user = userService.resetPasswordAndGetUser(token, requestPasswordChange.getNewPassword());
        UserDTO userDTO = userConverter.convert(user);
        return new Response<>(200,true,userDTO);
    }
}
