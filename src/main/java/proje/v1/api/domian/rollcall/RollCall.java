package proje.v1.api.domian.rollcall;

import lombok.Getter;
import lombok.Setter;
import proje.v1.api.base.domain.BaseModel;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.student.Student;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class RollCall extends BaseModel {

    @OneToMany(fetch = FetchType.LAZY)
    private List<Student> inComingStudents = new ArrayList<>();

}
