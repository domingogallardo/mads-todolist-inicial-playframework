package models;

public interface EquipoRepository {
    Equipo add(Equipo equipo);
    Equipo update(Equipo equipo);
    void delete(Long idEquipo);
    Equipo findById(Long idEquipo);
}
