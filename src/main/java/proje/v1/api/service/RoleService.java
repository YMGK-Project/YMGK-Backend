package proje.v1.api.service;

import org.springframework.stereotype.Service;
import proje.v1.api.domian.user.Users;
import proje.v1.api.exceptionhandler.NotFoundException;
import proje.v1.api.exceptionhandler.UnauthorizedException;

@Service
public class RoleService {
    public void validatePermission(Users user, String role) {
        if(user == null || !(user.getUserRole().toString().equals(role)))
            throw new UnauthorizedException("Bu işlemi gerçekleştirmek için yetkiniz yok");
    }

    public void validateIsUser(Users user){
        if(user == null)
            throw new NotFoundException("Herhangi bir kullanıcı bulunamadı.");
        boolean isTeacher = user.getUserRole().toString().equals("Teacher");
        boolean isSecretary = user.getUserRole().toString().equals("Secretary");
        boolean isStudent = user.getUserRole().toString().equals("Student");
        if(!(isTeacher || isSecretary || isStudent))
            throw new UnauthorizedException("Bu işlemi gerçekleştirmek için yetkiniz yok");
    }
}

