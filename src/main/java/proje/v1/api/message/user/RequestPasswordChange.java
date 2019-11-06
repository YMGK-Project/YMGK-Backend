package proje.v1.api.message.user;

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
public class RequestPasswordChange {

    @NotNull @Size(min = 6, max = 25)
    private String oldPassword;
    @NotNull @Size(min = 6, max = 25)
    private String newPassword;
}
