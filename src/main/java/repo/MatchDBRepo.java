package repo;

import domain.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MatchDBRepo implements MatchRepo{
    private JdbcUtils dbUtils;

    private static final Logger logger  = LogManager.getLogger();

    public MatchDBRepo(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Match add(Match entity) {
        logger.traceEntry("saving match {} ", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Matches values (?,?,?,?,?)")) {
            preStmt.setInt(1, entity.getId());
            preStmt.setString(2, entity.getTeamA());
            preStmt.setString(3, entity.getTeamB());
            preStmt.setDouble(4, entity.getTicketPrice());
//            preStmt.setInt(5, entity.getTotalSeats());
            preStmt.setInt(5, entity.getSoldSeats());

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
    public Match findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Match> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Match> matches = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Matches")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String teamA = result.getString("teamA");
                    String teamB = result.getString("teamB");
                    double ticketPrice = result.getDouble("ticketPrice");
                    int soldSeats = result.getInt("soldSeats");
                    Match match = new Match(id, teamA, teamB, ticketPrice, soldSeats);
                    matches.add(match);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(matches);
        return matches;
    }

    @Override
    public Match delete(Integer integer) {
        return null;
    }

    @Override
    public void update(Match entity) {
        logger.traceEntry("updating match {} ", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Matches set teamA=?, teamB=?, ticketPrice=?, soldSeats=? where id=?")) {
            preStmt.setString(1, entity.getTeamA());
            preStmt.setString(2, entity.getTeamB());
            preStmt.setDouble(3, entity.getTicketPrice());
//            preStmt.setInt(4, entity.getTotalSeats());
            preStmt.setInt(4, entity.getSoldSeats());
            preStmt.setInt(5, entity.getId());
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public boolean checkAvailableSeats(Match match, int numberOfSeatsToBuy) throws Exception {
        logger.traceEntry("checking available seats for match {} ", match);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Matches where id=?")) {
            preStmt.setInt(1, match.getId());
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int soldSeats = result.getInt("soldSeats");
                    if (soldSeats - numberOfSeatsToBuy < 0) {
                        logger.traceExit("Not enough seats available");
                        return false;
                    }else {
                        match.setSoldSeats(soldSeats - numberOfSeatsToBuy);
                        update(match);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("Enough seats available");
        return true;
    }
}
