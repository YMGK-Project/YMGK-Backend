package proje.v1.api.domian.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tokenholder")
public class TemporaryTokenHolder {

    @Id
    private String token;
    @OneToOne
    private Users users;
}
