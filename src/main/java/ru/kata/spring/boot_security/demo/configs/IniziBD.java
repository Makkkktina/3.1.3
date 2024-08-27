package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class IniziBD {
    private final UserService userService;
    private final RoleRepository roleRepository;


    @Autowired
    public IniziBD(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    @Transactional
    public void initializeUsers() {
        Role adminRole = new Role();
        adminRole.setRoleName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("ROLE_USER");
        roleRepository.save(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
        User adminUser = new User("makka","mmm", 20L, "11", "11",adminRoles);
        userService.save(adminUser);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        User regularUser = new User("ilyas","mmm", 23L, "22", "22",userRoles);        regularUser.setRoles(userRoles);
        userService.save(regularUser);
    }
}