import org.junit.*;
import static org.junit.Assert.*;

import models.Usuario;

public class UsuarioTest {

   @Test
   public void testCrearUsuario() {
      // Los parámetros del constructor son los campos obligatorios
      Usuario usuario = new Usuario("pepitoperez", "pepitoperez@gmail.com");
      usuario.nombre = "Pepito Pérez Fernández";
      usuario.password = "123456789";
      assertEquals("pepitoperez", usuario.login);
      assertEquals("pepitoperez@gmail.com", usuario.email);
      assertEquals("Pepito Pérez Fernández", usuario.nombre);
      assertEquals("123456789", usuario.password);
   }
}
