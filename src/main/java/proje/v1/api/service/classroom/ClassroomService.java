package proje.v1.api.service.classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.classroom.ClassroomRepository;
import proje.v1.api.domian.classroom.EducationType;
import proje.v1.api.domian.classroom.SectionType;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public Classroom addClassroom(String cod, String name, SectionType sectionType, EducationType educationType) {
        Classroom classroom = new Classroom();
        classroom.setCod(cod); classroom.setName(name);
        classroom.setSectionType(sectionType); classroom.setEducationType(educationType);
        return classroomRepository.save(classroom);
    }
}
