package proje.v1.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import proje.v1.api.auth.JwtProvider;
import proje.v1.api.base.messages.Response;
import proje.v1.api.base.util.BindingValidator;
import proje.v1.api.message.RequestLogin;
import proje.v1.api.service.SecretaryService;
import proje.v1.api.service.UserService;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private SecretaryService secretaryService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Response<String> login(@Valid @RequestBody RequestLogin requestLogin, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        userService.validateUser(requestLogin.getUsername(), requestLogin.getPassword());
        return new Response<>(200, true, jwtProvider.generateJsonWebToken(requestLogin.getUsername()));
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void qwe(){
        secretaryService.testSecretary();
    }
}
