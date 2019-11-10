package proje.v1.api.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.classroom.EducationType;
import proje.v1.api.domian.classroom.SectionType;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.teacher.TeacherRepository;
import proje.v1.api.domian.user.Users;
import proje.v1.api.service.classroom.ClassroomService;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassroomService classroomService;

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Classroom addClassroomAndGet(Users user, String cod, String name, SectionType sectionType, EducationType educationType) {
        Classroom classroom = classroomService.addClassroom(cod, name, sectionType, educationType);
        Teacher teacher = user.getTeacher();
        teacher.getClassroomList().add(classroom);
        teacherRepository.save(teacher);
        return classroom;
    }
}
