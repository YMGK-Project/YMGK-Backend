package proje.v1.api.service.classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.classroom.ClassroomRepository;
import proje.v1.api.domian.classroom.EducationType;
import proje.v1.api.domian.classroom.SectionType;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.exception.NotFoundException;

import java.util.List;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public void save(Classroom classroom){
        classroomRepository.save(classroom);
    }
    public List<Classroom> findAll(){
        return classroomRepository.findAll();
    }

    public Classroom addClassroom(String cod, String name, SectionType sectionType, EducationType educationType) {
        Classroom classroom = new Classroom();
        classroom.setCod(cod); classroom.setName(name);
        classroom.setSectionType(sectionType); classroom.setEducationType(educationType);
        return classroomRepository.save(classroom);
    }

    public List<RollCall> findAllRollCallsById(Long id) {
        Classroom classroom = classroomRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found any classroom"));
        return classroom.getRollCalls();
    }

    public Classroom findBy(Long classroomId) {
        return classroomRepository.findById(classroomId).
                orElseThrow(() -> new NotFoundException("Not found any classroom with id : "+ classroomId));
    }

    public Classroom findById(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found any classroom with id: "+id));
    }

    public Classroom saveAndGet(Classroom classroom) {
        return classroomRepository.save(classroom);
    }
}
