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
            if (tarea.getUsuario() == null) {
                throw new TareaRepositoryException("La tarea debe tener un usuario asociado");
            }
            entityManager.persist(tarea);
            entityManager.flush();
            entityManager.refresh(tarea);
            // Lo anterior es suficiente para que se actualice la relación
            // tarea-usuario en la base de datos. La siguiente línea
            // es para actualizar la relación en memoria, por si alguien
            // recupera el usuario de la tarea y accede a su lista de tareas.
            tarea.getUsuario().getTareas().add(tarea);
            return tarea;
        });
    }

    public Tarea update(Tarea tarea) {
        return jpaApi.withTransaction(entityManager -> {
            Tarea actualizada = entityManager.merge(tarea);
            return actualizada;
        });
    }

    public void delete(Tarea tarea) {
        jpaApi.withTransaction(() -> {
            EntityManager entityManager = jpaApi.em();
            Tarea tareaBD = entityManager.getReference(Tarea.class, tarea.getId());
            entityManager.remove(tareaBD);
        });
    }

    public Tarea findById(Long idTarea) {
        return jpaApi.withTransaction(entityManager -> {
            return entityManager.find(Tarea.class, idTarea);
        });
    }
}
