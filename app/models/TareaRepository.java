package models;

import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(JPATareaRepository.class)
public interface TareaRepository {
   Tarea add(Tarea tarea);
   Tarea findById(Long idTarea);
   List<Tarea> findAllTareas(Long idUsuario);
}
