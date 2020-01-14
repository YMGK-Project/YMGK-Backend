package proje.v1.api.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.classroom.ClassroomRepository;
import proje.v1.api.domian.classroom.EducationType;
import proje.v1.api.domian.classroom.SectionType;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.teacher.TeacherRepository;
import proje.v1.api.domian.user.Users;
import proje.v1.api.dto.rollcall.RollCallDTO;
import proje.v1.api.exception.NotFoundException;
import proje.v1.api.service.classroom.ClassroomService;
import proje.v1.api.service.rollcall.RollCallService;
import proje.v1.api.service.student.StudentService;
import proje.v1.api.service.user.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private UserService userService;

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
        System.out.println(teacher.getId());
        return teacher.getClassrooms();
    }

    public List<Users> startRollCall(Long classroomId) {
        rollCallService.startRollCallWith(classroomId);
        Classroom classroom = classroomService.findById(classroomId);
        List<Users> users = new ArrayList<>();
        classroom.getStudents().forEach(student -> users.add(userService.findByStudent(student.getId())));
        return users;
    }

    public RollCall finishRollCall(Long classroomId) {
        Classroom classroom = classroomService.findBy(classroomId);
        RollCall rollCall = rollCallService.finishRollCall(classroomId);
        rollCallService.deleteQrCode(classroomId);
        List<Student> nonComeStudent = findDiffStudentLists(classroom.getStudents(), rollCall.getInComingStudents());
        rollCall.setNonStudents(nonComeStudent);
        rollCall.getInComingStudents().forEach(studentService::save);
        rollCall.getNonStudents().forEach(studentService::save);
        System.out.println(rollCall.getId()+" id ");
        RollCall savedRollCall = rollCallService.save(rollCall);
        System.out.println(rollCall.getId()+" id ");
        classroom.getRollCalls().add(savedRollCall);
        classroomService.save(classroom);
        return savedRollCall;
    }

    private List<Student> findDiffStudentLists(List<Student> firstList, List<Student> secondList){
        int len = firstList.size();
        int len2 = secondList.size();
        List<Student> students = new ArrayList<>();
        boolean status = true;
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len2; j++) {
               if(firstList.get(i).getId().equals(secondList.get(j).getId()))
                   status = false;
            }
            if(status)
                students.add(firstList.get(i));
            status = true;
        }
        return students;
    }

    public void cancelRollCall(Long deviceId) {
        rollCallService.cancelRollCall(deviceId);
    }

    public void deleteClassroom(Long id) {
       //classroomService.deleteClassroomById(id);
    }

    public Classroom updateClassroomAndGet(Long id, String cod, String name, SectionType sectionType, EducationType educationType) {
        Classroom classroom=classroomService.findById(id);
        classroom.setCod(cod);
        classroom.setName(name);
        classroom.setSectionType(sectionType);
        classroom.setEducationType(educationType);
        return classroomService.saveAndGet(classroom);
    }
    public void deleteTeacherById(Long id) {
        teacherRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found any teacher with id: "+id));
        teacherRepository.deleteById(id);
    }
    public Teacher findById(Long id) {
        return teacherRepository.findById(id).get();
    }

    public RollCall getActiveRollCall(Long deviceId) {
        return rollCallService.getActiveRollCall(deviceId);
    }


    public RollCall finishRollCallWithFinger(String studentIds) {
        RollCall rollCall = new RollCall();
        Classroom classroom = classroomService.findBy(9L);
        for (int count=1; count < studentIds.length() ; count++){
            Student student = studentService.findByStudentId(count);
            if(studentIds.charAt(count) == '1')
                rollCall.getInComingStudents().add(student);
            else
                rollCall.getNonStudents().add(student);
        }
        RollCall savedRollCall = rollCallService.save(rollCall);
        classroom.getRollCalls().add(savedRollCall);
        classroomService.save(classroom);
        return savedRollCall;
    }

    public List<RollCall> getClassroomRollCalls(Long id) {
        Classroom classroom = classroomService.findById(id);
        return classroom.getRollCalls();
    }
}
