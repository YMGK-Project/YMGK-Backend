package proje.v1.api.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.teacher.TeacherRepository;
import proje.v1.api.domian.user.Users;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void saveClassroom(Users user, Classroom classroom) {
        Teacher teacher = user.getTeacher();
        teacher.getClassroomList().add(classroom);
        teacherRepository.save(teacher);
    }

}
