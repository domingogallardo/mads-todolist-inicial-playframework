package models;

import java.util.List;

public interface EquipoRepository {
    Equipo add(Equipo equipo);
    Equipo update(Equipo equipo);
    void delete(Equipo equipo);
    void addUsuarioEquipo(Usuario usuario, Equipo equipo);
    void deleteUsuarioEquipo(Usuario usuario, Equipo equipo);

    // Queries
    Equipo findById(Long idEquipo);
    List<Equipo> findAll();
    List<Usuario> findUsuariosEquipo(Long idEquipo);
}
