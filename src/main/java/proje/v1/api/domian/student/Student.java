package proje.v1.api.domian.student;

import lombok.Getter;
import lombok.Setter;
import proje.v1.api.base.domain.BaseModel;
import proje.v1.api.domian.classroom.Classroom;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Student extends BaseModel {

    private String fingerMark;
    @ManyToMany
    @JoinTable(
            name = "student_classroom",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id")
    )
    private List<Classroom> classrooms;
}
