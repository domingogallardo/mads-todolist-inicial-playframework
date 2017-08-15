import org.junit.*;
import static org.junit.Assert.*;

import models.Usuario;
import models.Tarea;

public class TareaTest {

   // Test #11: testCrearTarea
   @Test
   public void testCrearTarea() {
      Usuario usuario = new Usuario("juangutierrez", "juangutierrez@gmail.com");
      Tarea tarea = new Tarea(usuario, "Práctica 1 de MADS");

      assertEquals("juangutierrez", tarea.getUsuario().getLogin());
      assertEquals("juangutierrez@gmail.com", tarea.getUsuario().getEmail());
      assertEquals("Práctica 1 de MADS", tarea.getTitulo());
   }

   // Test #14: testEqualsTareasConId
   @Test
   public void testEqualsTareasConId() {
      Usuario usuario = new Usuario("juangutierrez", "juangutierrez@gmail.com");
      Tarea tarea1 = new Tarea(usuario, "Práctica 1 de MADS");
      Tarea tarea2 = new Tarea(usuario, "Renovar DNI");
      Tarea tarea3 = new Tarea(usuario, "Pagar el alquiler");
      tarea1.setId(1L);
      tarea2.setId(1L);
      tarea3.setId(2L);
      assertEquals(tarea1, tarea2);
      assertNotEquals(tarea1, tarea3);
   }

   // Test #15
   @Test
   public void testEqualsTareasSinId() {
      Usuario usuario = new Usuario("juangutierrez", "juangutierrez@gmail.com");
      Tarea tarea1 = new Tarea(usuario, "Renovar DNI");
      Tarea tarea2 = new Tarea(usuario, "Renovar DNI");
      Tarea tarea3 = new Tarea(usuario, "Pagar el alquiler");
      assertEquals(tarea1, tarea2);
      assertNotEquals(tarea1, tarea3);
   }
}
