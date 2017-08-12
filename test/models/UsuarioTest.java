import org.junit.*;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import models.Usuario;

public class UsuarioTest {

   @Test
   public void testCrearUsuario() throws ParseException {
      // Los parámetros del constructor son los campos obligatorios
      Usuario usuario = new Usuario("juangutierrez", "juangutierrez@gmail.com");
      usuario.nombre = "Juan";
      usuario.apellidos = "Gutierrez";
      usuario.password = "123456789";

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      usuario.fechaNacimiento = sdf.parse("1997-02-20");

      assertEquals("juangutierrez", usuario.login);
      assertEquals("juangutierrez@gmail.com", usuario.email);
      assertEquals("Juan", usuario.nombre);
      assertEquals("Gutierrez", usuario.apellidos);
      assertEquals("123456789", usuario.password);
      assertTrue(usuario.fechaNacimiento.compareTo(sdf.parse("1997-02-20")) == 0);
   }
}
