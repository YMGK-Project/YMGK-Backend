package proje.v1.api.service;

import org.springframework.stereotype.Service;
import proje.v1.api.domian.user.Users;
import proje.v1.api.exceptionhandler.ForbiddenException;

@Service
public class RoleService {
    public void validatePermission(Users user, String role) {
        if(user == null || !(user.getUserRole().toString().equals(role)))
            throw new ForbiddenException("Bu işlemi gerçekleştirmek için yetkiniz yok");
    }
}
