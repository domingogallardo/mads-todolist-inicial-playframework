import org.junit.*;
import static org.junit.Assert.*;

import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;

import play.Logger;

import java.sql.*;

import models.Usuario;
import models.Tarea;
import models.UsuarioRepository;
import models.JPAUsuarioRepository;
import models.TareaRepository;
import models.JPATareaRepository;

public class TareaTest {
   static Database db;
   static JPAApi jpaApi;

   // Se ejecuta sólo una vez, al principio de todos los tests
   @BeforeClass
   static public void initDatabase() {
      // Inicializamos la BD en memoria y su nombre JNDI
      db = Databases.inMemoryWith("jndiName", "DBTest");
      db.getConnection();
      // Se activa la compatibilidad MySQL en la BD H2
      db.withConnection(connection -> {
         connection.createStatement().execute("SET MODE MySQL;");
      });
      // Activamos en JPA la unidad de persistencia "memoryPersistenceUnit"
      // declarada en META-INF/persistence.xml y obtenemos el objeto
      // JPAApi
      jpaApi = JPA.createFor("memoryPersistenceUnit");
   }

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

   // Test #16: testAddTareaJPARepositoryInsertsTareaDatabase
   @Test
   public void testAddTareaJPARepositoryInsertsTareaDatabase() {
      assertNotNull(jpaApi);
      UsuarioRepository usuarioRepository = new JPAUsuarioRepository(jpaApi);
      TareaRepository tareaRepository = new JPATareaRepository(jpaApi);
      Usuario usuario = new Usuario("juangutierrez", "juangutierrez@gmail.com");
      usuario = usuarioRepository.add(usuario);
      Tarea tarea = new Tarea(usuario, "Renovar DNI");
      tarea = tareaRepository.add(tarea);
      Logger.info("Número de tarea: " + Long.toString(tarea.getId()));
      assertNotNull(tarea.getId());
      assertEquals("Renovar DNI", getTituloFromTareaDB(tarea.getId()));
   }

   private String getTituloFromTareaDB(Long tareaId) {
      String titulo = db.withConnection(connection -> {
         String selectStatement = "SELECT TITULO FROM TAREA WHERE ID = ? ";
         PreparedStatement prepStmt = connection.prepareStatement(selectStatement);
         prepStmt.setLong(1, tareaId);
         ResultSet rs = prepStmt.executeQuery();
         rs.next();
         return rs.getString("TITULO");
      });
      return titulo;
   }
}
