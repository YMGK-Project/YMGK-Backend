package proje.v1.api.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import proje.v1.api.common.util.Crypt;
import proje.v1.api.config.auth.ContextHolder;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.domian.user.TemporaryTokenHolder;
import proje.v1.api.domian.user.Users;
import proje.v1.api.message.user.RequestPasswordChange;
import proje.v1.api.message.user.RequestPasswordForgot;
import proje.v1.api.service.email.EmailService;
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
    private RoleService roleService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TemporaryTokenHolderService temporaryTokenHolderService;

    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    public Response<Users> changePassword(@Valid @RequestBody RequestPasswordChange requestPasswordChange, BindingResult bindingResult){
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

    @RequestMapping(value = "/forgot/password", method = RequestMethod.POST)
    public Response<String> forgotPassword(@Valid @RequestBody RequestPasswordForgot requestPasswordForgot, BindingResult bindingResult, HttpServletRequest request){
        BindingValidator.validate(bindingResult);
        userService.validateUserExist(requestPasswordForgot.getEmail());
        Users user = userService.findByEmail(requestPasswordForgot.getEmail()).get();
        String token = Crypt.getRandomUUID();
        temporaryTokenHolderService.saveTokenHolder(token, user);
        emailService.sendPasswordChangeMail(
                requestPasswordForgot.getEmail(),
                request.getScheme()+"://"+request.getServerName(),
                token
        );
        return new Response<>(200, true, "Email başarıyla gönderildi.");
    }

    @RequestMapping(value = "/reset/password/{token}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable String token){
        temporaryTokenHolderService.validateToken(token);
        TemporaryTokenHolder tokenHolder = temporaryTokenHolderService.findById(token);
        return "www.hasanatasoy.com/index/{token}";
    }
}
