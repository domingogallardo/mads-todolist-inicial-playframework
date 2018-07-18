package services;

import models.Equipo;
import models.EquipoRepository;
import models.Tarea;
import models.Usuario;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquipoService {
    EquipoRepository equipoRepository;

    @Inject
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }


    public Equipo addEquipo(String nombre) {
        if (equipoRepository.findByNombre(nombre) != null)
            throw new EquipoServiceException("Nombre de equipo ya existe: " + nombre);
        Equipo equipo = new Equipo(nombre);
        return equipoRepository.add(equipo);
    }

    // Devuelve la lista de equipos ordenadas por su id
    public List<Equipo> allEquipos() {
        List<Equipo> equipos = equipoRepository.findAll();
        Collections.sort(equipos, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        return equipos;
    }
}
