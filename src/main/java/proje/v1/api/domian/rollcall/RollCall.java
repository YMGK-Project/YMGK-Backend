package proje.v1.api.domian.rollcall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;
import proje.v1.api.common.domain.BaseModel;
import proje.v1.api.domian.student.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rollcall")
public class RollCall extends BaseModel{

    @OneToMany
    @JoinTable(name = "rollcall_incoming")
    @JsonIgnore
    private List<Student> inComingStudents = new ArrayList<>();
    @OneToMany
    @JoinTable(name = "rollcall_non")
    @JsonIgnore
    private List<Student> nonStudents = new ArrayList<>();

    public RollCall(List<Student> inComingStudents){
        this.inComingStudents = inComingStudents;
    }
}
