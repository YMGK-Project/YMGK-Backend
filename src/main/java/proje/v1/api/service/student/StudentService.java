package proje.v1.api.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.classroom.ClassroomRepository;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.student.StudentRepository;
import proje.v1.api.exception.NotFoundException;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public List<Classroom> findAllClassroomBy(Long id) {
        Student student = studentRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User Not Found"));
        return student.getClassrooms();
    }

    public Student findByFingerMark(String hashedFingerMark) {
        return studentRepository.findByFingerMark(hashedFingerMark).
                orElseThrow(()-> new NotFoundException("Not found any student with fingerMark : "+hashedFingerMark));
    }
    public List<Classroom> getClassrooms(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found any student with: "+id)).getClassrooms();
    }
    public void deleteStudentById(Long id) {
        studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Bu id: "+id+" ile herhangi bir öğrenci bulunamadı."));
        studentRepository.deleteById(id);
    }
    public Student findById(Long id) {
        return studentRepository.findById(id).get();
    }
}
