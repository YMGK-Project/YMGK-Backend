package proje.v1.api.message.rollcall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestManuelRollCallStudent {

    private Long deviceId;
    private Long studentId;
}
