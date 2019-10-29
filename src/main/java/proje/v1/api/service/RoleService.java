package proje.v1.api.service;

import org.springframework.stereotype.Service;
import proje.v1.api.domian.user.Users;
import proje.v1.api.exceptionhandler.UnauthorizedException;

@Service
public class RoleService {
    public void validatePermission(Users user, String role) {
        if(user == null || !(user.getUserRole().toString().equals(role)))
            throw new UnauthorizedException("Bu işlemi gerçekleştirmek için yetkiniz yok");
    }
}
