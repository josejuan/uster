package com.uster.backend;

import com.uster.backend.core.Backend;
import com.uster.model.Driver;
import com.uster.model.License;
import com.uster.model.Vehicle;
import com.uster.model.hibernate.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Function;
import java.util.stream.Stream;

public class HibernateBackend implements Backend {

    private final EntityManagerFactory db;

    public HibernateBackend() {
        db = Persistence.createEntityManagerFactory("usterBackend");
    }

    private <T> T query(Function<EntityManager, T> k) {
        return k.apply(db.createEntityManager());
    }

    private <T> T transaction(Function<EntityManager, T> k) {
        return query(db -> {
            db.getTransaction().begin();
            try {
                return k.apply(db);
            } finally {
                db.getTransaction().commit();
            }
        });
    }

    private <T, E extends ConvertTo<T>> Stream<T> list(Class<E> clazz) {
        return query(db -> db.createQuery("SELECT x FROM " + clazz.getSimpleName() + " x", clazz).getResultStream().map(ConvertTo::to));
    }

    private <I, T, E extends ConvertTo<T> & HasId<I>> T upsert(E e) {
        return transaction(db -> {
            if (e.getId() != null)
                return db.merge(e);
            db.persist(e);
            return e;
        }).to();
    }

    private <I, T, E extends ConvertTo<T> & HasId<I>> T remove(E e) {
        return transaction(db -> {
            db.remove(db.find(HVehicle.class, e.getId()));
            return e.to();
        });
    }

    @SuppressWarnings("unchecked")
    private <I, T, E extends ConvertTo<T> & HasId<I>> T lookup(E e) {
        return query(db -> db.find((Class<E>) e.getClass(), e.getId())).to();
    }

    @Override
    public Stream<License> licenses() {
        return list(HLicense.class);
    }

    @Override
    public Vehicle upsert(Vehicle vehicle) {
        return upsert(HVehicle.from(vehicle));
    }

    @Override
    public Vehicle remove(Vehicle vehicle) {
        return remove(HVehicle.from(vehicle)).withId(null);
    }

    @Override
    public Vehicle lookup(Vehicle vehicle) {
        return lookup(HVehicle.from(vehicle));
    }

    @Override
    public Stream<Vehicle> vehicles() {
        return list(HVehicle.class);
    }

    @Override
    public Driver upsert(Driver driver) {
        return upsert(HDriver.from(driver));
    }

    @Override
    public Driver remove(Driver driver) {
        return remove(HDriver.from(driver)).withId(null);
    }

    @Override
    public Driver lookup(Driver driver) {
        return lookup(HDriver.from(driver));
    }

    @Override
    public Stream<Driver> drivers() {
        return list(HDriver.class);
    }
}
