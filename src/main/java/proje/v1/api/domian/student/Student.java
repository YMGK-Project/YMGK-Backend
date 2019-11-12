package proje.v1.api.domian.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import proje.v1.api.common.domain.BaseModel;
import proje.v1.api.domian.classroom.Classroom;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseModel implements Serializable {

    private String fingerMark;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "student_classroom"
    )
    private List<Classroom> classrooms = new ArrayList<>();
}
