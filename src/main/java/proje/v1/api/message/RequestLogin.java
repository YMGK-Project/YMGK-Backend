package proje.v1.api.message;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestLogin {

    @NotNull @Size(min = 6, max = 30)
    private String username;
    @NotNull @Size(min = 6, max = 25)
    private String password;
}
