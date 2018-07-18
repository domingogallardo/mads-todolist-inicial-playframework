package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class JPATareaRepository implements TareaRepository {
    JPAApi jpaApi;

    @Inject
    public JPATareaRepository(JPAApi api) {
        this.jpaApi = api;
    }

    public Tarea add(Tarea tarea) {
        return jpaApi.withTransaction(entityManager -> {
            entityManager.persist(tarea);
            entityManager.flush();
            entityManager.refresh(tarea);
            return tarea;
        });
    }

    public Tarea update(Tarea tarea) {
        return jpaApi.withTransaction(entityManager -> {
            Tarea actualizada = entityManager.merge(tarea);
            return actualizada;
        });
    }

    public void delete(Long idTarea) {
        jpaApi.withTransaction(() -> {
            EntityManager entityManager = jpaApi.em();
            Tarea tareaBD = entityManager.getReference(Tarea.class, idTarea);
            entityManager.remove(tareaBD);
        });
    }

    public Tarea findById(Long idTarea) {
        return jpaApi.withTransaction(entityManager -> {
            return entityManager.find(Tarea.class, idTarea);
        });
    }
}
