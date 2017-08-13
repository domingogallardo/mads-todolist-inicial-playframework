package models;

import javax.inject.Inject;
import play.db.jpa.JPAApi;


public class JPAUsuarioRepository implements UsuarioRepository {
   // Objeto definido por Play para acceder al API de JPA
   // https://www.playframework.com/documentation/2.5.x/JavaJPA#Using-play.db.jpa.JPAApi
   JPAApi jpaApi;

   // Para usar el JPAUsuarioRepository hay que proporcionar una JPAApi
   public JPAUsuarioRepository(JPAApi api) {
      this.jpaApi = api;
   }

   public Usuario add(Usuario usuario) {
      return jpaApi.withTransaction(entityManager -> {
         entityManager.persist(usuario);
         // Hacemos un flush y un refresh para asegurarnos de que se realiza
         // la creación en la BD y se devuelve el id inicializado
         entityManager.flush();
         entityManager.refresh(usuario);
         return usuario;
      });
   }

   public Usuario findById(Long idUsuario) {
      return jpaApi.withTransaction(entityManager -> {
         return entityManager.find(Usuario.class, idUsuario);
      });
   }
}
