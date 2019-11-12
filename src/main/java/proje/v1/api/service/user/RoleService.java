package proje.v1.api.service.user;

import org.springframework.stereotype.Service;
import proje.v1.api.domian.user.Users;
import proje.v1.api.exception.ForbiddenException;
import proje.v1.api.exception.UnAuthenticationException;

@Service
public class RoleService {
    public void validatePermission(Users user, String role) {
        if(user == null)
            throw new UnAuthenticationException("Kimlik doğrulama yapmanız gerekiyor.");
        if(!(user.getUserRole().toString().equals(role)))
            throw new ForbiddenException("Bu işlemi gerçekleştirmek için yetkiniz yok");
    }

    public void validateIsUser(Users user){
        if(user == null)
            throw new UnAuthenticationException("Kimlik doğrulama yapmanız gerekiyor.");
        boolean isTeacher = user.getUserRole().toString().equals("Teacher");
        boolean isSecretary = user.getUserRole().toString().equals("Secretary");
        boolean isStudent = user.getUserRole().toString().equals("Student");
        if(!(isTeacher || isSecretary || isStudent))
            throw new ForbiddenException("Bu işlemi gerçekleştirmek için yetkiniz yok");
    }
}

