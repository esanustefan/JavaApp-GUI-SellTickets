package repo;


import domain.Entity;

import java.io.FileNotFoundException;


public interface Repository<ID, E extends Entity<ID>> {

    E findOne(ID id);

    Iterable<E> findAll();


    E add(E entity) throws FileNotFoundException;


    E delete(ID id);

    void update(E entity);
}