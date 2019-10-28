package proje.v1.api.service;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import proje.v1.api.base.util.Crypt;
import proje.v1.api.domian.Secretary.Secretary;
import proje.v1.api.domian.Secretary.SecretaryRepository;
import proje.v1.api.domian.Teacher.Teacher;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.user.UserRole;
import proje.v1.api.domian.user.Users;
import proje.v1.api.domian.user.UsersRepository;
import proje.v1.api.message.RequestTeacher;

import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

@Service
public class SecretaryService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SecretaryRepository secretaryRepository;

    public void testSecretary(){
        Secretary secretary = new Secretary();
        Secretary secretary1 = secretaryRepository.save(secretary);
        Users user = new Users("hasanatasoy", Crypt.hashWithSha256("12345678"), "hasan", "atasoy");
        user.setUserRole(UserRole.Secretary);
        user.setSecretary(secretary1);
        usersRepository.save(user);
    }

    public Users saveTeacher(String username, String password, String name, String surname) {
        Teacher teacher = new Teacher();
        Teacher teacher1 = teacherService.save(teacher);
        String passwordWithHash = Crypt.hashWithSha256(password);
        Users user = new Users(username, passwordWithHash, name, surname);
        user.setTeacher(teacher1);
        user.setUserRole(UserRole.Teacher);
        usersRepository.save(user);
        return user;
    }

    public Users saveStudent(String username, String password, String fingerMark, String name, String surname) {
        Student student = new Student();
        String fingerMarkWithHash = Crypt.hashWithSha256(fingerMark);
        student.setFingerMark(fingerMarkWithHash);
        Student student1 = studentService.save(student);
        String passwordWithHash = Crypt.hashWithSha256(password);
        Users user = new Users(username, passwordWithHash, name, surname);
        user.setStudent(student1);
        user.setUserRole(UserRole.Student);
        usersRepository.save(user);
        return user;
    }
}
