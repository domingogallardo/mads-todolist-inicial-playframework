package services;

import models.Equipo;
import models.EquipoRepository;

import javax.inject.Inject;

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
}
