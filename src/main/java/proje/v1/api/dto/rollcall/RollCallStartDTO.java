package proje.v1.api.dto.rollcall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proje.v1.api.common.util.RandomOperations;
import proje.v1.api.domian.user.Users;
import proje.v1.api.dto.user.UserDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RollCallStartDTO {

    private List<UserDTO> users;
    private String qrCode;

}
