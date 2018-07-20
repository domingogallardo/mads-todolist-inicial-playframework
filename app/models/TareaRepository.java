package models;

public interface TareaRepository {
    Tarea add(Tarea tarea);
    Tarea update(Tarea tarea);
    void delete(Tarea tarea);
    Tarea findById(Long idTarea);
}
