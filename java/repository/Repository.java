package repository;

import domain.Entity;

public interface Repository<ID, E extends Entity<ID>> {
    void save(E element);

    E findOne(ID id);

    void update(E oldElement, E newElement);

    E delete(ID id);

    Iterable<E> findAll();
}
