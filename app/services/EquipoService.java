package services;

import models.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquipoService {
    EquipoRepository equipoRepository;
    UsuarioRepository usuarioRepository;

    @Inject
    public EquipoService(EquipoRepository equipoRepository, UsuarioRepository usuarioRepository) {
        this.equipoRepository = equipoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Equipo addEquipo(String nombre) {
        Equipo equipo = new Equipo(nombre);
        return equipoRepository.add(equipo);
    }

    // Devuelve la lista de equipos ordenadas por su id
    public List<Equipo> allEquipos() {
        List<Equipo> equipos = equipoRepository.findAll();
        Collections.sort(equipos, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        return equipos;
    }

    public void addUsuarioEquipo(Long idUsuario, Long idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo);
        if (equipo == null) {
            throw new EquipoServiceException("No existe el equipo: " + idEquipo);
        }
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new EquipoServiceException("No existe el usuario: " + idUsuario);
        }
        equipoRepository.addUsuarioEquipo(usuario, equipo);
    }

    public void deleteUsuarioEquipo(Long idUsuario, Long idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo);
        if (equipo == null) {
            throw new EquipoServiceException("No existe el equipo: " + idEquipo);
        }
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new EquipoServiceException("No existe el usuario con username: " + idUsuario);
        }
        equipoRepository.deleteUsuarioEquipo(usuario, equipo);
    }

    public List<Usuario> findUsuariosEquipo(Long idEquipo) {
        List<Usuario> usuarios = new ArrayList<>();
        Equipo equipo = equipoRepository.findById(idEquipo);
        if (equipo != null) {
            usuarios = equipoRepository.findUsuariosEquipo(idEquipo);
        }
        return usuarios;
    }
}
