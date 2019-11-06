package proje.v1.api.domian.rollcall;

import lombok.Getter;
import lombok.Setter;
import proje.v1.api.common.domain.BaseModel;
import proje.v1.api.domian.student.Student;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class RollCall extends BaseModel {

    @OneToOne
    private Student student;
    private boolean isHere;

}
