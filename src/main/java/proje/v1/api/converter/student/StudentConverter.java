package proje.v1.api.converter.student;

import org.springframework.stereotype.Component;
import proje.v1.api.converter.Converter;
import proje.v1.api.domian.student.Student;
import proje.v1.api.dto.student.StudentDTO;

import java.util.List;

@Component
public class StudentConverter implements Converter<Student, StudentDTO> {
    @Override
    public StudentDTO convert(Student student) {
        return new StudentDTO(student.getId(), student.getFingerMark());
    }

    public List<StudentDTO> convert(List<Student> students){
        return null;
    }
}
