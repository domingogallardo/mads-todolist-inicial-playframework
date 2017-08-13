package services;

import javax.inject.*;

import models.Usuario;
import models.UsuarioRepository;


public class UsuarioService {
   UsuarioRepository repository;

   // Play proporcionará automáticamente el UsuarioRepository necesario
   // usando inyección de dependencias
   @Inject
   public UsuarioService(UsuarioRepository repository) {
      this.repository = repository;
   }

   public Usuario creaUsuario(String login, String email, String password) {
      Usuario usuario = new Usuario(login, email);
      usuario.setPassword(password);
      return repository.add(usuario);
   }
}
