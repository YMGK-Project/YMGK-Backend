package proje.v1.api.message.rollcall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestRollCallStudent {

    private Long deviceId;
    private String fingerMark;
}
