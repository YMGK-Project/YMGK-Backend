package proje.v1.api.domian.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import proje.v1.api.domian.user.Users;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
