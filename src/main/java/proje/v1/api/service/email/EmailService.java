package proje.v1.api.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    
    //şifre sıfırlama için mail gönderen adres
    private String mailAddress="mail@adres";
    
    
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendPasswordChangeMail(String email, String serverUrl, String token){
        String url = serverUrl+"/user/reset/password/"+token;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Şifre Sıfırlama İsteği");
        simpleMailMessage.setText("Şifrenizi değiştirmek için aşağıdaki linke tıklayınız.\n"+url);
        mailSender.send(simpleMailMessage);
    }
    
     public boolean checkPasswordChange(String pass1,String pass2) {
	if(pass1 == pass2) {

		return true;
	}
	
	return false;

   }
}
