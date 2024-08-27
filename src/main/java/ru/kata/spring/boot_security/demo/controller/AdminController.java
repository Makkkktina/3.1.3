package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Transactional
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public String showAllUsers(ModelMap modelMap, @AuthenticationPrincipal User user) {
        modelMap.addAttribute("users", userService.showUsers());
        modelMap.addAttribute("user", user);
        return "admin/admin";
    }

    @GetMapping("/create")
    public String addNewUser(ModelMap modelMap, @AuthenticationPrincipal User user) {
        User newUser = new User();
        Map<String, Object> model = new HashMap<>();
        model.put("user", newUser);
        model.put("listRoles", userService.listRoles());
        model.put("currentUser", user);
        modelMap.addAllAttributes(model);
        return "admin/create";
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String updateUser(@PathVariable("id") Long id, ModelMap modelMap, @AuthenticationPrincipal User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("user", userService.findUserWithRoles(id));
        model.put("listRoles", userService.listRoles());
        model.put("currentUser", user);
        modelMap.addAllAttributes(model);
        return "admin/update";
    }

    @PatchMapping("/update")
    public String saveUpdateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public String handleNotFoundException(ChangeSetPersister.NotFoundException ex) {
        return "error/not_found";
    }
}