package proje.v1.api.service.secretary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.common.util.Crypt;
import proje.v1.api.domian.secretary.Secretary;
import proje.v1.api.domian.secretary.SecretaryRepository;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.user.UserRole;
import proje.v1.api.domian.user.Users;
import proje.v1.api.service.student.StudentService;
import proje.v1.api.service.teacher.TeacherService;
import proje.v1.api.service.user.UserService;

@Service
public class SecretaryService {

    @Autowired
    private UserService userService;
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
        userService.save(user);
    }

    public Users saveTeacher(String username, String password, String name, String surname) {
        Teacher teacher = new Teacher();
        Teacher teacher1 = teacherService.save(teacher);
        String passwordWithHash = Crypt.hashWithSha256(password);
        Users user = new Users(username, passwordWithHash, name, surname);
        user.setTeacher(teacher1);
        user.setUserRole(UserRole.Teacher);
        userService.save(user);
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
        userService.save(user);
        return user;
    }
}
