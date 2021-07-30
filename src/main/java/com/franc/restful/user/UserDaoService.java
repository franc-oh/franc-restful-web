package com.franc.restful.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static int userCount;
    private static List<User> users = new ArrayList<>();

    static {
        users.add(User.builder().id(1).name("Mike").joinDate(new Date()).build());
        users.add(User.builder().id(2).name("Tom").joinDate(new Date()).build());
        users.add(User.builder().id(3).name("Jack").joinDate(new Date()).build());
        userCount = 3;
    }


    public List<User> findAll() {
        return users;
    }


    public User findOne(int id) {

        for(User u : users) {
            if(u.getId() == id)
                return u;
        }

        return null;
    }


    public User save(User user) {
        if(user.getId() == null)
            user.setId(++userCount);

        //user.setJoinDate(new Date());
        users.add(user);
        return user;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User u = iterator.next();

            if(u.getId() == id) {
                iterator.remove();
                return u;
            }
        }

        return null;
    }
}
