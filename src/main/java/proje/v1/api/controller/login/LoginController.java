package proje.v1.api.controller.login;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import proje.v1.api.config.auth.JwtProvider;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.converter.user.UserConverter;
import proje.v1.api.domian.user.Users;
import proje.v1.api.dto.login.LoginDTO;
import proje.v1.api.dto.user.UserDTO;
import proje.v1.api.message.login.RequestLogin;
import proje.v1.api.service.user.UserService;
import javax.validation.Valid;

@Api(description = "Giriş İşlemleri")
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    @ApiOperation(value = "Kullanıcının giriş yapmasını sağlar")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Response<LoginDTO> login(@Valid @RequestBody RequestLogin requestLogin, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        Users user = userService.findByUsernameAndPassword(requestLogin.getUsername(), requestLogin.getPassword());
        UserDTO userDTO = userConverter.convert(user);
        String jwt = jwtProvider.generateJsonWebToken(requestLogin.getUsername());
        LoginDTO loginDTO = new LoginDTO(userDTO, jwt);
        return new Response<>(200, true, loginDTO);
    }

}
