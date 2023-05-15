package repo;

import domain.Match;

import java.io.FileNotFoundException;

public interface MatchRepo extends Repository<Integer, Match>{
    @Override
    Match findOne(Integer integer);

    @Override
    Iterable<Match> findAll();

    @Override
    Match add(Match entity) throws FileNotFoundException;

    @Override
    Match delete(Integer integer);

    @Override
    void update(Match entity);

    boolean checkAvailableSeats(Match match, int numberOfSeatsToBuy) throws Exception;
}
