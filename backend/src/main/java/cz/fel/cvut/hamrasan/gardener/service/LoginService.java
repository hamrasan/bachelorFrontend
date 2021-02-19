package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.exceptions.AlreadyLoginException;
import cz.fel.cvut.hamrasan.gardener.model.User;
import cz.fel.cvut.hamrasan.gardener.security.DefaultAuthenticationProvider;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private DefaultAuthenticationProvider provider;
    private UserDao userDao;

    @Autowired
    public LoginService(DefaultAuthenticationProvider provider, UserDao userDao) {
        this.provider = provider;
        this.userDao = userDao;
    }


    @Transactional(readOnly = true)
    public User login(/*String username*/ String email, String password) throws AlreadyLoginException {

        if (SecurityUtils.getCurrentUserDetails() != null) throw new AlreadyLoginException();
        Authentication auth = new UsernamePasswordAuthenticationToken(/*username*/ email, password);
        provider.authenticate(auth);
        return userDao.find(SecurityUtils.getCurrentUser().getId());

    }

}