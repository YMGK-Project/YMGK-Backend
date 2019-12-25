package proje.v1.api.converter.user;

import org.springframework.stereotype.Component;
import proje.v1.api.common.domain.BaseModel;
import proje.v1.api.converter.Converter;
import proje.v1.api.domian.user.Users;
import proje.v1.api.dto.user.UserDTO;

@Component
public class UserConverter implements Converter<Users, UserDTO> {
    @Override
    public UserDTO convert(Users user) {
        BaseModel dtoUser = getUserJobFrom(user);
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getUserRole(),
                dtoUser,
                user.getImgURL()
        );
    }

    private BaseModel getUserJobFrom(Users users){
        switch (users.getUserRole()){
            case Student:
                return users.getStudent();
            case Teacher:
                return users.getTeacher();
            case Secretary:
                return users.getSecretary();
            default:
                return null; // böyle bir ihtimal mümkün değil
        }
    }
}
