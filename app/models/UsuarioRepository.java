package models;

public interface UsuarioRepository {
    Usuario add(Usuario usuario);
    Usuario findById(Long id);
    Usuario findByLogin(String login);
}
