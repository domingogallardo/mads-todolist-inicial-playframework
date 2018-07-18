package models;

public interface TareaRepository {
    Tarea add(Tarea tarea);
    Tarea update(Tarea tarea);
    void delete(Long idTarea);
    Tarea findById(Long idTarea);
}
