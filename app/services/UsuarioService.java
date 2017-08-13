package services;

import models.Usuario;
import models.UsuarioRepository;


public class UsuarioService {
   UsuarioRepository repository;

   public UsuarioService(UsuarioRepository repository) {
      this.repository = repository;
   }

   public Usuario creaUsuario(String login, String email, String password) {
      Usuario usuario = new Usuario(login, email);
      usuario.setPassword(password);
      return repository.add(usuario);
   }
}
