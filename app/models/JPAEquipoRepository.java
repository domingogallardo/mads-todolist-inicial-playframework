package models;

import javax.inject.Inject;

import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class JPAEquipoRepository implements EquipoRepository {
    JPAApi jpaApi;

    @Inject
    public JPAEquipoRepository(JPAApi api) {
        this.jpaApi = api;
    }

    @Override
    public Equipo add(Equipo equipo) {
        return jpaApi.withTransaction(entityManager -> {
            entityManager.persist(equipo);
            entityManager.flush();
            entityManager.refresh(equipo);
            return equipo;
        });
    }

    @Override
    public Equipo update(Equipo equipo) {
        return jpaApi.withTransaction(entityManager -> {
            Equipo actualizado = entityManager.merge(equipo);
            return actualizado;
        });
    }

    @Override
    public void delete(Long idEquipo) {
        jpaApi.withTransaction( () -> {
            EntityManager entityManager = jpaApi.em();
            Equipo equipoBD = entityManager.getReference(Equipo.class, idEquipo);
            entityManager.remove(equipoBD);
        });
    }

    @Override
    public Equipo findById(Long idEquipo) {
        return jpaApi.withTransaction(entityManager -> {
            return entityManager.find(Equipo.class, idEquipo);
        });
    }

    @Override
    public Equipo findByNombre(String nombre) {
        return jpaApi.withTransaction(entityManager -> {
            TypedQuery<Equipo> query = entityManager.createQuery(
                    "select u from Equipo u where u.nombre = :nombre", Equipo.class);
            try {
                Equipo equipo = query.setParameter("nombre", nombre).getSingleResult();
                return equipo;
            } catch (NoResultException ex) {
                return null;
            }
        });
    }
}
