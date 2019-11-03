package proje.v1.api.domian.teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import proje.v1.api.base.domain.BaseModel;
import proje.v1.api.domian.classroom.Classroom;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends BaseModel {

    @OneToMany(fetch = FetchType.EAGER)
    private List<Classroom> classroomList = new ArrayList<>();
}
