package proje.v1.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    public String helloworld(){
        return "Hello World";
    }
}
