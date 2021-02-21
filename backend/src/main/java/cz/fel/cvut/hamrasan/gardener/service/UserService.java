package cz.fel.cvut.hamrasan.gardener.service;

import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.exceptions.BadPassword;
import cz.fel.cvut.hamrasan.gardener.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }


    public User find(Long id) {
        return userDao.find(id);
    }


    public User find(String name) {
        return userDao.findByEmail(name);
    }

    @Transactional
    public void createUser(User user, String passwordAgain) throws BadPassword {
        Objects.requireNonNull(user);
        if (!user.getPassword().equals(passwordAgain)) throw new BadPassword();
        user.encodePassword();

        userDao.persist(user);
    }




}
