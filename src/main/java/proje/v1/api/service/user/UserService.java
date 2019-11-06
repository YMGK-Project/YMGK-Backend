package proje.v1.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.common.util.Crypt;
import proje.v1.api.domian.user.Users;
import proje.v1.api.domian.user.UsersRepository;
import proje.v1.api.exception.BadRequestExcepiton;
import proje.v1.api.exception.NotFoundException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UsersRepository userRepository;

    public Optional<Users> findById(String username){
        return userRepository.findById(username);
    }

    public void validateUser(String username, String password) {
        Optional<Users> user = userRepository.findById(username);
        boolean userIsExist = user.isPresent();
        if(!userIsExist)
            throw new NotFoundException("User not exist with : "+username);
        String passWithHashing = Crypt.hashWithSha256(password);
        if(!(user.get().getPassword().equals(passWithHashing)))
            throw new BadRequestExcepiton("Password is not correct");
    }

    public void validateUserIsNotExist(String username) {
        boolean userIsExist = userRepository.findById(username).isPresent();
        if(userIsExist)
            throw new BadRequestExcepiton("Username already taken as : "+username);
    }

    public void resetPassword(String username, String newPassword) {
        Users user = userRepository.findById(username).get();
        user.setPassword(Crypt.hashWithSha256(newPassword));
        userRepository.save(user);
    }

    public void save(Users user) {
        userRepository.save(user);
    }
}
