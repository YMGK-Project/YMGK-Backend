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
import java.util.concurrent.atomic.AtomicInteger;

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
				initClassroomAndStudentAndRollCall(classroomService, studentService, userService, teacherService);
				initTeacher(teacherService, userService);
				initSecretary(secretaryService, userService);
		};
	}

	private void initTeacher(TeacherService teacherService, UserService userService) {
		Teacher teacher2 = new Teacher();
		Teacher teacher3 = teacherService.save(teacher2);
		Users user1 = new Users("teacher2",Crypt.hashWithSha256("teacher2"),"Erkan", "Tanyıldızlı");
		user1.setEmail("erkantanyildizli@gmail.com");
		user1.setImgURL("https://abs.firat.edu.tr/upload/user_435/6b5053c331386534b8220c043375f391b5c2ffc1_image_435.jpg");
		user1.setUserRole(UserRole.Teacher);
		user1.setTeacher(teacher3);
		userService.save(user1);
	}

	private Users initFatihOzkaynak(TeacherService teacherService, UserService userService){
		Teacher teacher = new Teacher();
		Teacher teacher1 = teacherService.save(teacher);
		Users user = new Users("teacher",Crypt.hashWithSha256("teacher"),"fatih", "özkaynak");
		user.setEmail("fatihözkaynak@gmail.com");
		user.setImgURL("http://www.kriptarium.com/uploads/4/1/0/0/41002523/published/screenshot-2017-07-29-15-41-46.png?1501335230");
		user.setUserRole(UserRole.Teacher);
		user.setTeacher(teacher1);
		userService.save(user);
		return userService.findById("teacher");
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

	private void initClassroomAndStudentAndRollCall(ClassroomService classroomService, StudentService studentService, UserService userService,
	TeacherService teacherService) {
		Users user = initFatihOzkaynak(teacherService, userService);
		List<Classroom> classrooms = Arrays.asList(
				new Classroom(
						null,
						null,
						"101",
						"Yazılım Mühendisliğinde Güncel Konular",
						SectionType.A,
						EducationType.First,
						"3+2"),
				new Classroom(
						null,
						null,
						"102",
						"Yazılım Mühendisliği Oryantasyonu",
						SectionType.A,
						EducationType.First,
						"3+2")
		);
		List<Student> students = Arrays.asList(
				new Student(Crypt.hashWithSha256("PARMAKIZI1"), null),
				new Student(Crypt.hashWithSha256("PARMAKIZI2"), null),
				new Student(Crypt.hashWithSha256("PARMAKIZI3"), null),
				new Student(Crypt.hashWithSha256("PARMAKIZI4"), null),
				new Student(Crypt.hashWithSha256("PARMAKIZI5"), null),
				new Student(Crypt.hashWithSha256("PARMAKIZI6"), null),
				new Student(Crypt.hashWithSha256("PARMAKIZI7"), null)
		);
		List<Users> users = Arrays.asList(
				new Users("deneme", Crypt.hashWithSha256("deneme"),"Hüseyin","Gürsoy"),
				new Users("deneme1", Crypt.hashWithSha256("deneme1"),"Sertan","Sayımbay"),
				new Users("deneme2", Crypt.hashWithSha256("deneme2"),"Yunus","Yılmaz"),
				new Users("deneme3", Crypt.hashWithSha256("deneme3"),"Mert","Aydemir"),
				new Users("deneme4", Crypt.hashWithSha256("deneme4"),"Emre","Güven"),
				new Users("deneme5", Crypt.hashWithSha256("deneme5"),"Tunahan","Aydos"),
				new Users("deneme6", Crypt.hashWithSha256("deneme6"),"Semih","Akyüz")
		);
		users.forEach(users1 -> users1.setUserRole(UserRole.Student));
		students.forEach(studentService::save);
		List<Student> savedStudent = studentService.findAll();
		AtomicInteger count = new AtomicInteger();
		users.forEach(users1 -> {
			users1.setStudent(savedStudent.get(count.get()));
			count.getAndIncrement();
		});
		users.forEach(userService::save);
		classrooms.get(0).setStudents(savedStudent);
		classrooms.forEach(classroomService::save);
		List<Classroom> savedClassroom = classroomService.findAll();
		Teacher teacher = user.getTeacher();
		teacher.setClassrooms(savedClassroom);
		Teacher teacher1 = teacherService.save(teacher);
		user.setTeacher(teacher1);
		userService.save(user);
	}

}
