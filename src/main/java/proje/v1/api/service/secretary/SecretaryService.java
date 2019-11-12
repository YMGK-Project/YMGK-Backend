package proje.v1.api.service.secretary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.common.util.Crypt;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.classroom.ClassroomRepository;
import proje.v1.api.domian.secretary.Secretary;
import proje.v1.api.domian.secretary.SecretaryRepository;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.teacher.TeacherRepository;
import proje.v1.api.domian.user.UserRole;
import proje.v1.api.domian.user.Users;
import proje.v1.api.exception.NotFoundException;
import proje.v1.api.service.classroom.ClassroomService;
import proje.v1.api.service.student.StudentService;
import proje.v1.api.service.teacher.TeacherService;
import proje.v1.api.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private TeacherRepository teacherRepository;

    public Users saveTeacherAndGet(String email, String username, String password, String name, String surname) {
        userService.validateUserIsNotExist(username);
        Teacher teacher = new Teacher();
        Teacher teacher1 = teacherService.save(teacher);
        String passwordWithHash = Crypt.hashWithSha256(password);
        Users user = new Users(username, passwordWithHash, name, surname);
        user.setTeacher(teacher1);
        user.setUserRole(UserRole.Teacher);
        user.setEmail(email);
        return userService.saveAndGet(user);
    }

    public Users saveStudentAndGet(String email, String username, String password, String fingerMark, String name, String surname) {
        userService.validateUserIsNotExist(username);
        Student student = new Student();
        String fingerMarkWithHash = Crypt.hashWithSha256(fingerMark);
        student.setFingerMark(fingerMarkWithHash);
        Student student1 = studentService.save(student);
        String passwordWithHash = Crypt.hashWithSha256(password);
        Users user = new Users(username, passwordWithHash, name, surname);
        user.setStudent(student1);
        user.setUserRole(UserRole.Student);
        user.setEmail(email);
        return userService.saveAndGet(user);
    }

    public Secretary save(Secretary secretary) {
        return secretaryRepository.save(secretary);
    }

}
