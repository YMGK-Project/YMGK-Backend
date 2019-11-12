package proje.v1.api.message.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestFinishRollCall {

    private Long classroomId;
    private Long deviceId;

}
