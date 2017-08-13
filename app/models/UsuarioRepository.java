package models;

import com.google.inject.ImplementedBy;

// Interfaz que define los métodos del UsuarioRepository
// La anotación ImplementedBy hace que Play para resolver la
// inyección de dependencias escoja como objeto que implementa
// esta interfaz un objeto JPAUsuarioRepository
@ImplementedBy(JPAUsuarioRepository.class)
public interface UsuarioRepository {
   Usuario add(Usuario usuario);
   Usuario findById(Long id);
   Usuario findByLogin(String login);
}
