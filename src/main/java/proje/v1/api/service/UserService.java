package proje.v1.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.base.util.Crypt;
import proje.v1.api.domian.user.Users;
import proje.v1.api.domian.user.UsersRepository;
import proje.v1.api.exceptionhandler.BadRequestExcepiton;
import proje.v1.api.exceptionhandler.NotFoundException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UsersRepository userRepository;

    public Optional<Users> findById(String username){
        return userRepository.findById(username);
    }

    public void validateLogin(String username, String password) {
        Optional<Users> user = userRepository.findById(username);
        boolean userIsExist = user.isPresent();
        if(!userIsExist)
            throw new NotFoundException("User not exist with : "+username);
        if(!(user.get().getPassword().equals(Crypt.hashWithSha256(password))))
            throw new BadRequestExcepiton("Password is not correct");
    }

    public void validateUserIsNotExist(String username) {
        boolean userIsExist = userRepository.findById(username).isPresent();
        if(userIsExist)
            throw new BadRequestExcepiton("Username already taken as : "+username);
    }
}
