package repo;

import domain.Ticket;
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

public class TicketDBRepo implements TicketRepo{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public TicketDBRepo(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public Ticket findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Ticket> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Tickets")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String clientName = result.getString("clientName");
                    int numberOfSeats = result.getInt("numberOfSeats");
                    Ticket ticket = new Ticket(id, clientName, numberOfSeats);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(tickets);
        return tickets;
    }

    @Override
    public Ticket add(Ticket entity) throws FileNotFoundException {
        logger.traceEntry("saving ticket {} ", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Tickets values (?,?,?)")) {
            preStmt.setInt(1, entity.getId());
            preStmt.setString(2, entity.getClientName());
            preStmt.setInt(3, entity.getNumberOfSeats());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Ticket delete(Integer integer) {
        logger.traceEntry("deleting ticket with {}", integer);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Tickets where id=?")) {
            preStmt.setInt(1, integer);
            int result = preStmt.executeUpdate();
            logger.trace("Deleted {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void update(Ticket entity) {
        logger.traceEntry("updating ticket {} ", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Tickets set clientName=?, numberOfSeats=? where id=?")) {
            preStmt.setString(1, entity.getClientName());
            preStmt.setInt(2, entity.getNumberOfSeats());
            preStmt.setInt(3, entity.getId());
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
    }
}
