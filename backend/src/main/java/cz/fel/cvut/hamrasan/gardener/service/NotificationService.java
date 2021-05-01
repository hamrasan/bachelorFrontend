package cz.fel.cvut.hamrasan.gardener.service;

import com.hazelcast.core.HazelcastInstance;
import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotFoundException;
import cz.fel.cvut.hamrasan.gardener.model.User;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class NotificationService {

    private UserDao userDao;
//    private final HazelcastInstance cacheInstance;
//    private Map<Long, List<String>> cache;

    @Autowired
    public NotificationService(UserDao userDao) {

        this.userDao = userDao;
//        this.cacheInstance = hazelcastInstance;
//        this.cache = cacheInstance.getMap("notifications");

    }

//    @Transactional
//    public List<String> getNotifications() throws NotAllowedException {
//        if(SecurityUtils.isAuthenticatedAnonymously()) throw new NotAllowedException("Login first");
//
//        User user = userDao.find(SecurityUtils.getCurrentUser().getId());
//        List<String> notifcations = cache.get(user.getId());
//        return notifcations;
//        return null;
//    }

//    private List<String> getNotifications(Long id) throws NotFoundException {
//        User user = userDao.find(id);
//        if (user == null) throw new NotFoundException("User not found");
//        return cache.get(user.getId());
//        return null;
//    }

//    @Transactional
//    public void createListNotifications(Long id) {
//        cache.put(id, new ArrayList<>());
//    }

//    @Transactional
//    public void addNotification(Long id, String message) throws NotFoundException {
//        User user = userDao.find(id);
//        if (user == null) throw new NotFoundException("User not found");
//        if(!cache.containsKey(user.getId())) throw new NotFoundException("User in cache not found");
//        List<String> nofications = getNotifications(user.getId());
//        nofications.add(message);
//        cache.replace(user.getId(), nofications);
//    }
}
