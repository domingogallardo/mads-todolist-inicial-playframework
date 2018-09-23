package models;

import javax.inject.Inject;

import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

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
            return entityManager.merge(equipo);
        });
    }

    @Override
    public void delete(Equipo equipo) {
        jpaApi.withTransaction( () -> {
            EntityManager entityManager = jpaApi.em();
            Equipo equipoBD = entityManager.getReference(Equipo.class, equipo.getId());
            entityManager.remove(equipoBD);
        });
    }

    @Override
    public void addUsuarioEquipo(Usuario usuario, Equipo equipo) {
        jpaApi.withTransaction( () -> {
            EntityManager entityManager = jpaApi.em();
            // El usuario y equipo recibidos están desconectados del entity manager
            // por lo que tenemos que recuperarlos de la base de datos
            Equipo equipoBD = entityManager.find(Equipo.class, equipo.getId());
            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getId());
            // El método addUsuario de Equipo actualiza los campos y el
            // cambio se actualiza automáticamente a la base de datos
            equipoBD.addUsuario(usuarioBD);
            // Actualizamos también los campos en los objetos de memoria que nos
            // han pasado
            // equipo.addUsuario(usuario);
        });
    }

    @Override
    public void deleteUsuarioEquipo(Usuario usuario, Equipo equipo) {
        jpaApi.withTransaction( () -> {
            EntityManager entityManager = jpaApi.em();
            Equipo equipoBD = entityManager.find(Equipo.class, equipo.getId());
            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getId());
            equipoBD.removeUsuario(usuarioBD);
            // Actualizamos también los campos en los objetos de memoria que nos
            // han pasado
            equipo.removeUsuario(usuario);
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
                    "select e from Equipo e where e.nombre = :nombre", Equipo.class);
            try {
                Equipo equipo = query.setParameter("nombre", nombre).getSingleResult();
                return equipo;
            } catch (NoResultException ex) {
                return null;
            }
        });
    }

    @Override
    public List<Equipo> findAll() {
        return jpaApi.withTransaction(entityManager -> {
            TypedQuery<Equipo> query = entityManager.createQuery(
                    "select e from Equipo e", Equipo.class);
            return query.getResultList();
        });
    }

    public List<Usuario> findUsuariosEquipo(String nombreEquipo) {
        return jpaApi.withTransaction(entityManager -> {
            TypedQuery<Usuario> query = entityManager.createQuery(
                    "select u from Usuario u join u.equipos e where e.nombre = :nombreEquipo", Usuario.class);
            try {
                return query.setParameter("nombreEquipo", nombreEquipo).getResultList();
            } catch (NoResultException ex) {
                return null;
            }
        });
    }
}
