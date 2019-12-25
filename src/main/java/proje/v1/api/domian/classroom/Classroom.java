package proje.v1.api.domian.classroom;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import proje.v1.api.common.domain.BaseModel;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.domian.student.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Classroom extends BaseModel {

    @OneToMany
    @JoinTable(name = "classroom_rollcals")
    @JsonIgnore
    private List<RollCall> rollCalls = new ArrayList<>();
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "classroom_students"
    )
    private List<Student> students = new ArrayList<>();
    private String cod;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private SectionType sectionType;
    @Enumerated(value = EnumType.STRING)
    private EducationType educationType;
    private String credit;

    public Classroom(String cod, String name, SectionType sectionType, EducationType educationType){
        this.cod = cod;
        this.name = name;
        this.sectionType = sectionType;
        this.educationType = educationType;
    }
}
