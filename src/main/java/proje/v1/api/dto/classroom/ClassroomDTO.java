package proje.v1.api.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proje.v1.api.domian.classroom.EducationType;
import proje.v1.api.domian.classroom.SectionType;
import proje.v1.api.domian.rollcall.RollCall;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDTO {

    private Long id;
    private List<RollCall> rollCalls;
    private String cod;
    private String name;
    private SectionType sectionType;
    private EducationType educationType;
    private int studentCount;
    private String credit;
}
