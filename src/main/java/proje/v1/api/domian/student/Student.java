package proje.v1.api.domian.student;

import lombok.Getter;
import lombok.Setter;
import proje.v1.api.base.domain.BaseModel;
import proje.v1.api.domian.classroom.Classroom;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Student extends BaseModel {

    private String fingerMark;
    @ManyToMany
    private List<Classroom> classrooms;
}
