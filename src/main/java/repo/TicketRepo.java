package repo;

import domain.Ticket;

import java.io.FileNotFoundException;

public interface TicketRepo extends Repository<Integer, Ticket> {
    @Override
    Ticket findOne(Integer integer);

    @Override
    Iterable<Ticket> findAll();

    @Override
    Ticket add(Ticket entity) throws FileNotFoundException;

    @Override
    Ticket delete(Integer integer);

    @Override
    void update(Ticket entity);

}

