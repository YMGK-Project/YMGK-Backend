package proje.v1.api.dto.rollcall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proje.v1.api.dto.student.StudentDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RollCallDTO {

    private Long id;
    private List<StudentDTO> inComingStudent;
    private List<StudentDTO> nonStudent;
}
