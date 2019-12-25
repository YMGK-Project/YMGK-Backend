package proje.v1.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proje.v1.api.common.domain.BaseModel;
import proje.v1.api.domian.user.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String email;
    private String name;
    private String surname;
    private UserRole userRole;
    private BaseModel user;
    private  String imageUrl;
}
