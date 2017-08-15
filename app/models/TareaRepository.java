package models;

import com.google.inject.ImplementedBy;

@ImplementedBy(JPATareaRepository.class)
public interface TareaRepository {
   Tarea add(Tarea tarea);
   Tarea findById(Long idTarea);
}
