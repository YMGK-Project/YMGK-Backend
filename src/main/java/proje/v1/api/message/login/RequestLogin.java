package proje.v1.api.message.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestLogin {

    @NotNull @Size(min = 6, max = 30)
    private String username;
    @NotNull @Size(min = 6, max = 25)
    private String password;
}
