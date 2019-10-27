package proje.v1.api.message;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestStudent {

    @NotNull @NotNull @Size(min = 6, max = 35)
    String username;
    @NotNull @Size(min = 6, max = 26)
    String password;
    @NotNull
    String fingerMark;
    @NotNull
    String name;
    @NotNull
    String surname;
}
