package proje.v1.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proje.v1.api.domian.user.TemporaryTokenHolder;
import proje.v1.api.domian.user.TokenHolderRepository;
import proje.v1.api.domian.user.Users;
import proje.v1.api.exception.NotFoundException;

@Service
public class TemporaryTokenHolderService {

    @Autowired
    private TokenHolderRepository tokenHolderRepository;

    public TemporaryTokenHolder findById(String token){
        return tokenHolderRepository.findById(token).get();
    }

    public void validateToken(String token){
        boolean isExist = tokenHolderRepository.findById(token).isPresent();
        if(!isExist)
            throw new NotFoundException("Not found any token as : "+token);
    }

    public void saveTokenHolder(String token, Users user){
        TemporaryTokenHolder temporaryTokenHolder = new TemporaryTokenHolder();
        temporaryTokenHolder.setToken(token);
        temporaryTokenHolder.setUsers(user);
        tokenHolderRepository.save(temporaryTokenHolder);
    }
}
