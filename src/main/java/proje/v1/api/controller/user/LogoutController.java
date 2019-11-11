package proje.v1.api.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proje.v1.api.common.messages.Response;

import javax.servlet.http.HttpServletRequest;

/**
 * @author  
 * @version 1
 */
@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

    @Autowired
    TokenStore tokenStore;

    /**
     * 
     *
     * @param request
     * @return response<>
     * @exception proje.v1.api.exception.BadRequestExcepiton 
     * header'dan jwt token'i alip siler
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
    public Response<String > logout(HttpServletRequest request ) {

        String aut=request.getHeader("Authorization");
        if (aut != null) {
            String token=aut.replace("Bearer","").trim();
            //OAuth2AccessToken
            OAuth2AccessToken oAuth2AccessToken=tokenStore.readAccessToken(token);
            tokenStore.removeAccessToken(oAuth2AccessToken);

            return new Response<>(200,true,"cikis yapildi");
        }
        return new Response<>(400,true,"hata olustu");
    }
}
