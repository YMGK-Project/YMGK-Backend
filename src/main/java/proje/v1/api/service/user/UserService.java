package proje.v1.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.common.util.Crypt;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.user.TemporaryTokenHolder;
import proje.v1.api.domian.user.Users;
import proje.v1.api.domian.user.UsersRepository;
import proje.v1.api.exception.BadRequestExcepiton;
import proje.v1.api.exception.NotFoundException;
import proje.v1.api.exception.UnAuthenticationException;
import proje.v1.api.service.email.EmailService;
import proje.v1.api.service.student.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private TemporaryTokenHolderService temporaryTokenHolderService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StudentService studentService;

    public void save(Users user) {
        userRepository.save(user);
    }

    public Users saveAndGet(Users user){
        return userRepository.save(user);
    }

    public Users findById(String username){
        return userRepository.findById(username).
                orElseThrow(() -> new NotFoundException("User not exist with : "+username));
    }

    public Users findByStudent(Long studentId){
        Student student = studentService.findById(studentId);
        return userRepository.findByStudent(student).orElseThrow(()-> new NotFoundException("Not found student with id = "+studentId));
    }
    private Users findByEmail(String email){
        return userRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("User not exist with email : : "+email));
    }

    public Users findByUsernameAndPassword(String username, String password) {
        String passWithHashing = Crypt.hashWithSha256(password);
        return userRepository.findByUsernameAndPassword(username, passWithHashing).
                orElseThrow(()-> new UnAuthenticationException("Username or Password incorrect."));
    }

    public void validateUserIsNotExist(String username) {
        boolean userIsExist = userRepository.findById(username).isPresent();
        if(userIsExist)
            throw new BadRequestExcepiton("Username already taken as : "+username);
    }

    public Users changePasswordAndGetUser(String username, String password, String newPassword) {
        Users user = findByUsernameAndPassword(username, password);
        user.setPassword(Crypt.hashWithSha256(newPassword));
        return userRepository.save(user);
    }

    public void forgotPassword(String email, String scheme, String serverName) {
        Users user = findByEmail(email);
        String token = UUID.randomUUID().toString();
        temporaryTokenHolderService.saveTokenHolder(token, user);
        emailService.sendPasswordChangeMail(
                email,
                scheme+"://"+serverName,
                token
        );
    }

    public Users resetPasswordAndGetUser(String token, String newPassword) {
        TemporaryTokenHolder tokenHolder = temporaryTokenHolderService.findById(token);
        return changePasswordAndGetUser(
                tokenHolder.getUsers().getUsername(),
                tokenHolder.getUsers().getPassword(),
                newPassword);
    }
}
