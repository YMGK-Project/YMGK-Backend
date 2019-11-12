package proje.v1.api.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.classroom.ClassroomRepository;
import proje.v1.api.domian.classroom.EducationType;
import proje.v1.api.domian.classroom.SectionType;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.teacher.TeacherRepository;
import proje.v1.api.domian.user.Users;
import proje.v1.api.exception.NotFoundException;
import proje.v1.api.service.classroom.ClassroomService;
import proje.v1.api.service.rollcall.RollCallService;
import proje.v1.api.service.student.StudentService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private RollCallService rollCallService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassroomRepository classroomRepository;

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Classroom addClassroomAndGet(Users user, String cod, String name, SectionType sectionType, EducationType educationType) {
        Classroom classroom = classroomService.addClassroom(cod, name, sectionType, educationType);
        Teacher teacher = user.getTeacher();
        teacher.getClassrooms().add(classroom);
        teacherRepository.save(teacher);
        return classroom;
    }

    public List<Classroom> findAllClassroomBy(Long id) {
        Teacher teacher = teacherRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found any teacher with id: "+id));
        return teacher.getClassrooms();
    }

    public void startRollCall(Long deviceId) {
        rollCallService.startRollCall(deviceId);
    }

    public RollCall finishRollCall(Long classroomId, Long deviceId) {
        Classroom classroom = classroomService.findBy(classroomId);
        RollCall rollCall = rollCallService.finishRollCall(deviceId);
        rollCall.getInComingStudents().forEach(studentService::save);
        rollCall.getNonStudents().forEach(studentService::save);
        RollCall savedRollCall = rollCallService.save(rollCall);
        classroom.getRollCalls().add(savedRollCall);
        classroomService.save(classroom);
        return savedRollCall;
    }

    public void cancelRollCall(Long deviceId) {
        rollCallService.cancelRollCall(deviceId);
    }

    public Classroom findClassroomBy(Long id) {
        Classroom classroom=classroomRepository.findById(id);
        return classroom;
        // yapılacak
    }

    public void deleteClassroom(Long id) {

        classroomService.deleteClassroomById(id);
    }

    public Classroom updateClassroomAndGet(Long id, String cod, String name, SectionType sectionType, EducationType educationType) {
        boolean status =classroomRepository.findById(id).isPresent();
        if (!status) {
            throw new NotFoundException("sinif bulunamadi");
        }
        Classroom classroom=classroomRepository.findById(id);
        classroom.setCod(cod);
        classroom.setName(name);
        classroom.setSectionType(sectionType);
        classroom.setEducationType(educationType);
        classroomRepository.save(classroom);

        return classroom; // yapılacak
    }
    public void deleteTeacherById(Long id) {
        boolean status=teacherRepository.findById(id).isPresent();
        if(!status) {
            throw new NotFoundException("silinecek kayit bulunamadi");
        }
        teacherRepository.deleteById(id);
    }
    public Teacher findById(Long id) {
        return teacherRepository.findById(id);
    }


}
