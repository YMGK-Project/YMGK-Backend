package proje.v1.api.message;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestTeacher {

    @NotNull @Size(min = 6, max = 35)
    private String username;
    @NotNull @Size(min = 6, max = 26)
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String surname;
}
