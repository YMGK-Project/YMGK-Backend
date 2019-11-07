package proje.v1.api.message.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPasswordForgot {

    @NotNull
    @Size(min = 10,max = 100)
    @Email
    private String email;
}
