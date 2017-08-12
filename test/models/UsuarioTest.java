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
      usuario.setNombre("Juan");
      usuario.setApellidos("Gutierrez");
      usuario.setPassword("123456789");

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      usuario.setFechaNacimiento(sdf.parse("1997-02-20"));

      assertEquals("juangutierrez", usuario.getLogin());
      assertEquals("juangutierrez@gmail.com", usuario.getEmail());
      assertEquals("Juan", usuario.getNombre());
      assertEquals("Gutierrez", usuario.getApellidos());
      assertEquals("123456789", usuario.getPassword());
      assertTrue(usuario.getFechaNacimiento().compareTo(sdf.parse("1997-02-20")) == 0);
   }
}
