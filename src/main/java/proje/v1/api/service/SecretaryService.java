package proje.v1.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.Teacher.Teacher;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.user.UserRole;
import proje.v1.api.domian.user.Users;
import proje.v1.api.domian.user.UsersRepository;
import proje.v1.api.message.RequestTeacher;

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


    public Users saveTeacher(String username, String password, String name, String surname) {
        Teacher teacher = new Teacher();
        Teacher teacher1 = teacherService.save(teacher);
        Users user = new Users(username, password, name, surname);
        user.setTeacher(teacher1);
        user.setUserRole(UserRole.Teacher);
        usersRepository.save(user);
        return user;
    }

    public void checkPermission(Principal userPrincipal) {
    }

    public Users saveStudent(String username, String password, String fingerMark, String name, String surname) {
        Student student = new Student();
        student.setFingerMark(fingerMark);
        Student student1 = studentService.save(student);
        Users user = new Users(username, password, name, surname);
        user.setStudent(student1);
        user.setUserRole(UserRole.Student);
        return user;
    }
}
