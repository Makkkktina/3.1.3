package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
        return user;
    }


    @Transactional
    public void save(User user) {
        user.setRoles(user.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> showUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> Hibernate.initialize(user.getRoles()));
        return users;
    }

    @Override
    public void save(org.springframework.security.core.userdetails.User user) {

    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(org.springframework.security.core.userdetails.User user) {

    }

    @Transactional
    public void update(User user) {
        user.setRoles(user.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public User findUserWithRoles(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Hibernate.initialize(user.getRoles());
        return user;
    }
}