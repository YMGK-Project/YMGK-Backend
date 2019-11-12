package proje.v1.api.converter.teacher;

import org.springframework.stereotype.Component;
import proje.v1.api.converter.Converter;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.dto.teacher.TeacherDTO;

import java.util.List;

@Component
public class TeacherConverter implements Converter<Teacher, TeacherDTO> {
    @Override
    public TeacherDTO convert(Teacher teacher) {
        return null;
    }

    public List<TeacherDTO> convert(List<Teacher> teachers){
        return null;
    }
}
