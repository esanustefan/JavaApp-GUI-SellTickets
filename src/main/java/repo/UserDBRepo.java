package repo;

import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDBRepo implements UserRepo{

    private JdbcUtils dbUtils;

    private static final Logger logger  = LogManager.getLogger();

    public UserDBRepo(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public boolean checkIfAccountExists(String username, String password) {
        logger.traceEntry("finding user with {}", username);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Users where username=? and password=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,password);
            try(ResultSet result = preStmt.executeQuery()){
                if (result.next()){
                    int id = result.getInt("id");
                    String username1 = result.getString("username");
                    String password1 = result.getString("password");
                    User user = new User(id, username1, password1);
                    logger.traceExit(user);
                    return true;
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit("No user found with id {}", username);
        return false;
    }


    @Override
    public User findOne(Integer integer) {
        logger.traceEntry("finding user with {}", integer);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Users where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()){
                if (result.next()){
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(id, username, password);
                    logger.traceExit(user);
                    return user;
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit("No user found with id {}", integer);
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Users")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(id, username, password);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(users);
        return users;
    }

    @Override
    public User add(User entity) throws FileNotFoundException {
        logger.traceEntry("saving user {} ", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into Users values (?,?,?)")){
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
            int result = preStmt.executeUpdate();
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(entity);
        return null;
    }

    @Override
    public User delete(Integer integer) {
        return null;
    }

    @Override
    public void update(User entity) {
    }
}
