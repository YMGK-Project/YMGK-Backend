package proje.v1.api.message.student;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestStudent {

    @Email @NotNull @Size(min = 10, max = 30)
    private String email;
    @NotNull @NotNull @Size(min = 6, max = 35)
    private String username;
    @NotNull @Size(min = 6, max = 26)
    private String password;
    @NotNull
    private String fingerMark;
    @NotNull
    private String name;
    @NotNull
    private String surname;
}
