package proje.v1.api.domian.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proje.v1.api.common.domain.BaseModel;
import proje.v1.api.domian.classroom.Classroom;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends BaseModel {

    @OneToMany
    @JoinTable(name = "teacher_classrooms")
    private List<Classroom> classrooms = new ArrayList<>();
}
