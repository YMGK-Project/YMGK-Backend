package proje.v1.api.domian.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import proje.v1.api.domian.classroom.Classroom;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
