package proje.v1.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import proje.v1.api.common.util.Crypt;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.classroom.EducationType;
import proje.v1.api.domian.classroom.SectionType;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.domian.secretary.Secretary;
import proje.v1.api.domian.student.Student;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.user.UserRole;
import proje.v1.api.domian.user.Users;
import proje.v1.api.service.classroom.ClassroomService;
import proje.v1.api.service.rollcall.RollCallService;
import proje.v1.api.service.secretary.SecretaryService;
import proje.v1.api.service.student.StudentService;
import proje.v1.api.service.teacher.TeacherService;
import proje.v1.api.service.user.UserService;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(
			StudentService studentService,
			TeacherService teacherService,
			SecretaryService secretaryService,
			UserService userService,
			ClassroomService classroomService,
			RollCallService rollCallService){
		return args -> {
				initClassroomAndStudentAndRollCall(classroomService, studentService, rollCallService);
				initTeacher(teacherService, userService);
				initSecretary(secretaryService, userService);
		};
	}

	private void initTeacher(TeacherService teacherService, UserService userService) {
		Teacher teacher = new Teacher();
		Teacher teacher1 = teacherService.save(teacher);
		Users user = new Users("teacher",Crypt.hashWithSha256("teacher"),"fatih", "özkaynak");
		user.setEmail("fatihözkaynak@gmail.com");
		user.setUserRole(UserRole.Teacher);
		user.setTeacher(teacher1);
		userService.save(user);
	}

	private void initSecretary(SecretaryService secretaryService, UserService userService) {
		Secretary secretary = new Secretary();
		Secretary secretary1 = secretaryService.save(secretary);
		Users user = new Users("secretary", Crypt.hashWithSha256("secretary"),"xxxx", "xxxx");
		user.setEmail("xxxxxxxxx@gmail.com");
		user.setUserRole(UserRole.Secretary);
		user.setSecretary(secretary1);
		userService.save(user);
	}

	private void initClassroomAndStudentAndRollCall(ClassroomService classroomService, StudentService studentService, RollCallService rollCallService) {
		List<Classroom> classrooms = Arrays.asList(
				new Classroom(null, null, "101", "Birinci Ders", SectionType.A, EducationType.First),
				new Classroom(null, null, "102", "İkinci Ders", SectionType.A, EducationType.Secondary),
				new Classroom(null, null, "103", "Üçüncü Ders", SectionType.B, EducationType.First)
		);
		List<Student> students = Arrays.asList(
				new Student(Crypt.hashWithSha256("PARMAKIZI1"), null),
				new Student(Crypt.hashWithSha256("PARMAKIZI2"), null),
				new Student(Crypt.hashWithSha256("PARMAKIZI3"), null)
		);
		students.forEach(studentService::save);
		classrooms.forEach(classroomService::save);
		List<Student> savedStudents = studentService.findAll();
		List<RollCall> rollCalls = Arrays.asList(
				new RollCall(savedStudents),
				new RollCall(),
				new RollCall()
		);
		rollCalls.forEach(rollCallService::save);
		List<RollCall> savedRollCalls = rollCallService.findAll();
		List<Classroom> savedClassrooms = classroomService.findAll();
		savedClassrooms.forEach(classroom -> classroom.setStudents(savedStudents));
		savedClassrooms.get(0).setRollCalls(savedRollCalls);
		savedClassrooms.forEach(classroomService::save);
		List<Classroom> dSavedClassroom = classroomService.findAll();
		savedStudents.forEach(student -> student.setClassrooms(dSavedClassroom));
		savedStudents.forEach(studentService::save);
	}

}
