package ru.kata.spring.boot_security.demo.service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    List<ru.kata.spring.boot_security.demo.model.User> showUsers();

    void save(User user);
    ru.kata.spring.boot_security.demo.model.User findUserWithRoles(Long id);
    ru.kata.spring.boot_security.demo.model.User getUser(Long id);

    void delete(Long id);

    void update(User user);

    ru.kata.spring.boot_security.demo.model.User findByEmail(String email);

    List<Role> listRoles();

    void save(ru.kata.spring.boot_security.demo.model.User adminUser);

    void update(ru.kata.spring.boot_security.demo.model.User user);
}