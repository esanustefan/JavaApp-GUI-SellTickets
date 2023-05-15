package service;

import domain.Match;
import domain.Ticket;
import domain.User;
import repo.MatchRepo;
import repo.TicketRepo;
import repo.UserRepo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Service {
    private UserRepo repoUser;
    private MatchRepo repoMatch;
    private TicketRepo repoTicket;

    public Service(UserRepo repoUser, MatchRepo repoMatch, TicketRepo repoTicket) {
        this.repoUser = repoUser;
        this.repoMatch = repoMatch;
        this.repoTicket = repoTicket;
    }

    public Iterable<User> getAllUsers() {
        return repoUser.findAll();
    }

    public User findLoggedInUser(String username, String password) throws IOException {
        for(User u: repoUser.findAll()){
            if(Objects.equals(u.getUsername(), username) && Objects.equals(u.getPassword(), password)){
                return u;
            }
        }
        return null;
    }

    public boolean checkUserExists(String username, String password){
        return repoUser.checkIfAccountExists(username, password);
    }


    public Iterable<Match> getAllMatches() {
        return repoMatch.findAll();
    }

    public Iterable<Ticket> getAllTickets() {
        return repoTicket.findAll();
    }

    public void addTicket(Ticket ticket) throws FileNotFoundException {
        repoTicket.add(ticket);
    }

    public void deleteTicket(Integer id) {
        repoTicket.delete(id);
    }

    public void updateTicket(Ticket ticket) {
        repoTicket.update(ticket);
    }

    public void addUser(User user) throws FileNotFoundException {
        repoUser.add(user);
    }

    public void updateUser(User user) {
        repoUser.update(user);
    }

    public void addMatch(Match match) throws FileNotFoundException {
        repoMatch.add(match);
    }

    public void deleteMatch(Integer id) {
        repoMatch.delete(id);
    }

    public void updateMatch(Match match) {
        repoMatch.update(match);
    }

    public boolean checkAvailableSeats(Match match, int numberOfSeatsToBuy) throws Exception {
        return repoMatch.checkAvailableSeats(match, numberOfSeatsToBuy);
    }
}
