package repository;

import domain.Entity;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    protected Map<ID, E> map;

    public AbstractRepository() {

        this.map = new HashMap<>();
    }

    @Override
    public void save(E element) {
        map.put(element.getId(), element);
    }

    @Override
    public E findOne(ID id) {
        return this.map.get(id);
    }

    @Override
    public void update(E oldElement, E newElement) {
        if (oldElement.getId() != newElement.getId())
            return;
        if (map.containsKey(oldElement.getId()))
            this.map.put(newElement.getId(), newElement);
    }

    @Override
    public E delete(ID id) {
        E element = map.get(id);
        if (element != null)
            map.remove(id);
        return element;
    }

    @Override
    public Iterable<E> findAll() {
        return map.values();
    }

}
