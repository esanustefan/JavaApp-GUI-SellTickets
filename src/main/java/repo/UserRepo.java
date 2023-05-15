package repo;

import domain.User;

import java.io.FileNotFoundException;

public interface UserRepo extends Repository<Integer, User> {

    boolean checkIfAccountExists(String username, String password);

    @Override
    User findOne(Integer integer);

    @Override
    Iterable<User> findAll();

    @Override
    User add(User entity) throws FileNotFoundException;

    @Override
    User delete(Integer integer);

    @Override
    void update(User entity);
}
