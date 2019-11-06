package proje.v1.api.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.student.StudentRepository;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
