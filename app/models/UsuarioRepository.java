package models;

// Interfaz que define los métodos del UsuarioRepository
public interface UsuarioRepository {
   Usuario add(Usuario usuario);
   Usuario findById(Long id);
   Usuario findByLogin(String login);
}
