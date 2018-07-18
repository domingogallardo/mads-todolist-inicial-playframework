package models;

import java.util.List;

public interface EquipoRepository {
    Equipo add(Equipo equipo);
    Equipo update(Equipo equipo);
    void delete(Long idEquipo);
    Equipo findById(Long idEquipo);
    Equipo findByNombre(String nombre);
    List<Equipo> findAll();
}
