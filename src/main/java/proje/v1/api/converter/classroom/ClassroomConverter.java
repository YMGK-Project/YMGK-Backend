package proje.v1.api.converter.classroom;

import org.springframework.stereotype.Component;
import proje.v1.api.converter.Converter;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.dto.classroom.ClassroomDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassroomConverter implements Converter<Classroom, ClassroomDTO> {
    @Override
    public ClassroomDTO convert(Classroom classroom) {
        return new ClassroomDTO(
                classroom.getId(),
                classroom.getRollCalls(),
                classroom.getCod(),
                classroom.getName(),
                classroom.getSectionType(),
                classroom.getEducationType(),
                classroom.getStudents().size(),
                classroom.getCredit()
        );
    }

    public List<ClassroomDTO> convert(List<Classroom> classrooms){
        List<ClassroomDTO> classroomDTOS = new ArrayList<>();
        classrooms.forEach(classroom -> classroomDTOS.add(convert(classroom)));
        return classroomDTOS;
    }
}
