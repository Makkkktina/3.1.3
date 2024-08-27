package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaolmp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void creatUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updatUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    public User getUserId(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void delUser(Long id) {
        entityManager.remove(getUserId(id));
    }
}
