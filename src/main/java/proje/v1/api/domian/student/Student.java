package proje.v1.api.domian.student;

import lombok.Getter;
import lombok.Setter;
import proje.v1.api.base.domain.BaseModel;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Student extends BaseModel {

    String fingerMark;
}
