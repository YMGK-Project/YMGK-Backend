package proje.v1.api.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import proje.v1.api.common.messages.Response;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

    @Autowired
    //TokenStore tokenStore;

    @RequestMapping(value = "/user", method = {RequestMethod.GET,RequestMethod.POST})
    public Response<String > logout(HttpServletRequest request ) {

        /*String aut=request.getHeader("Authorization");
        if (aut != null) {
            String token=aut.replace("Bearer","").trim();
            //OAuth2AccessToken oAuth2AccessToken=tokenStore.readAccessToken(token);
            //tokenStore.removeAccessToken(oAuth2AccessToken);

            return new Response<>(200,true,"cikis yapildi");
        }*/
        return new Response<>(400,true,"hata olustu");
    }
}
