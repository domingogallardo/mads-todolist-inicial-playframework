package models;
import com.google.inject.ImplementedBy;

public interface EquipoRepository {
    Equipo add(Equipo equipo);
    Equipo update(Equipo equipo);
    void delete(Long idEquipo);
    Equipo findById(Long idEquipo);
}
