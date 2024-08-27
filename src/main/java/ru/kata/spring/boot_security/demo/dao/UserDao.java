package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void creatUser(User user);

    void updatUser(User user);

    List<User> getAllUsers();

    User getUserId(Long id);

    void delUser(Long id);
}