package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class JPAUsuarioRepository implements UsuarioRepository {
    // Objeto definido por Play para acceder al API de JPA
    // https://www.playframework.com/documentation/2.5.x/JavaJPA#Using-play.db.jpa.JPAApi
    JPAApi jpaApi;

    // Para usar el JPAUsuarioRepository hay que proporcionar una JPAApi.
    // La anotación Inject hace que Play proporcione el JPAApi cuando se lance
    // la aplicación.
    @Inject
    public JPAUsuarioRepository(JPAApi api) {
        this.jpaApi = api;
    }

    public Usuario add(Usuario usuario) {
        return jpaApi.withTransaction(entityManager -> {
            // Si existe un usuario con el mismo login lanzamos una excepción
            if (findByLogin(usuario.getLogin()) != null) {
                throw new UsuarioRepositoryException("Login repetido");
            }
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

    public Usuario findByLogin(String login) {
        return jpaApi.withTransaction(entityManager -> {
            TypedQuery<Usuario> query = entityManager.createQuery(
                    "select u from Usuario u where u.login = :login", Usuario.class);
            try {
                Usuario usuario = query.setParameter("login", login).getSingleResult();
                return usuario;
            } catch (NoResultException ex) {
                return null;
            }
        });
    }
}
