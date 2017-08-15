package models;

import javax.inject.Inject;
import play.db.jpa.JPAApi;

import java.util.List;

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
