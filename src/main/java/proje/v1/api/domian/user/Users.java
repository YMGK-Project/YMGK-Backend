package proje.v1.api.domian.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proje.v1.api.domian.secretary.Secretary;
import proje.v1.api.domian.teacher.Teacher;
import proje.v1.api.domian.student.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {

    @Id
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @OneToOne
    private Teacher teacher;
    @OneToOne
    private Secretary secretary;
    @OneToOne
    private Student student;
    private Date createdAt;
    private Date updatedAt;
    private String imgURL;

    public Users(String username, String password, String name, String surname){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    @PrePersist
    public void createdAt(){
        createdAt = new Date();
    }

    @PostPersist
    public void updatedAt(){
        updatedAt = new Date();
    }
}
