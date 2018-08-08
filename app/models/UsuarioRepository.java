package models;

public interface UsuarioRepository {
    Usuario add(Usuario usuario);

    // Queries

    Usuario findById(Long id);
    Usuario findByLogin(String login);
}
