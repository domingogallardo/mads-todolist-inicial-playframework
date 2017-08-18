package models;

import javax.inject.Inject;
import play.db.jpa.JPAApi;

import java.util.List;

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
         Tarea tareaBD = entityManager.find(Tarea.class, tarea.getId());
         tareaBD.setTitulo(tarea.getTitulo());
         return tareaBD;
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

   public List<Tarea> findAllTareas(Long idUsuario) {
      return jpaApi.withTransaction(entityManager -> {
         Usuario usuario = entityManager.find(Usuario.class, idUsuario);
         // Cargamos todas las tareas del usuario en memoria
         usuario.getTareas().size();
         return usuario.getTareas();
      });
   }
}
