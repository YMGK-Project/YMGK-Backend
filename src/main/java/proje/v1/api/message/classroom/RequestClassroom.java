package proje.v1.api.message.classroom;

import lombok.Getter;
import lombok.Setter;
import proje.v1.api.domian.classroom.EducationType;
import proje.v1.api.domian.classroom.SectionType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestClassroom {

    @NotNull @Size(min = 3, max = 7)
    private String cod;
    @NotNull @Size(min = 6)
    private String name;
    @NotNull
    private SectionType sectionType;
    @NotNull
    private EducationType educationType;
}
