package services;

import models.Tarea;
import models.TareaRepository;
import models.Usuario;
import models.UsuarioRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TareaService {
    UsuarioRepository usuarioRepository;
    TareaRepository tareaRepository;

    @Inject
    public TareaService(UsuarioRepository usuarioRepository, TareaRepository tareaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tareaRepository = tareaRepository;
    }

    // Devuelve la lista de tareas de un usuario, ordenadas por su id
    // (equivalente al orden de creación)
    public List<Tarea> allTareasUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new TareaServiceException("Usuario no existente id: " + idUsuario);
        }
        List<Tarea> tareas = new ArrayList(usuario.getTareas());
        Collections.sort(tareas, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        return tareas;
    }

    public Tarea nuevaTarea(Long idUsuario, String titulo) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new TareaServiceException("Usuario no existente id: " + idUsuario);
        }
        Tarea tarea = new Tarea(usuario, titulo);
        return tareaRepository.add(tarea);
    }

    public Tarea obtenerTarea(Long idTarea) {
        return tareaRepository.findById(idTarea);
    }

    public Tarea modificaTarea(Long idTarea, String nuevoTitulo) {
        Tarea tarea = tareaRepository.findById(idTarea);
        if (tarea == null)
            throw new TareaServiceException("No existe tarea id: " + idTarea);
        tarea.setTitulo(nuevoTitulo);
        tarea = tareaRepository.update(tarea);
        return tarea;
    }

    public void borraTarea(Long idTarea) {
        Tarea tarea = tareaRepository.findById(idTarea);
        if (tarea == null)
            throw new TareaServiceException("No existe tarea id: " + idTarea);
        tareaRepository.delete(tarea);
    }

    public void cambiaUsuarioTarea(Long idTarea, Long idNuevoUsuario) {
        Usuario nuevoUsuario = usuarioRepository.findById(idNuevoUsuario);
        if (nuevoUsuario == null)
            throw new TareaServiceException("No existe usuario id: " + idNuevoUsuario);
        Tarea tarea = tareaRepository.findById(idTarea);
        if (tarea == null)
            throw new TareaServiceException("No existe tarea id: " + idTarea);
        tarea.setUsuario(nuevoUsuario);
        tareaRepository.update(tarea);
    }
}
