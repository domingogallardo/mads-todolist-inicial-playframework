import org.junit.*;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;

import play.Logger;

import java.sql.*;

import models.Usuario;
import models.UsuarioRepository;
import models.JPAUsuarioRepository;

public class UsuarioTest {
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

   // Test 1: testCrearUsuario
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

   // Test 2: testAddUsuarioJPARepositoryInsertsUsuarioDatabase
   @Test
   public void testAddUsuarioJPARepositoryInsertsUsuarioDatabase() {
      assertNotNull(jpaApi);
      UsuarioRepository repository = new JPAUsuarioRepository(jpaApi);
      Usuario usuario = new Usuario("juangutierrez", "juangutierrez@gmail.com");
      usuario.setNombre("Juan");
      usuario.setApellidos("Gutierrez");
      usuario.setPassword("123456789");
      usuario = repository.add(usuario);
      Logger.info("Número de usuario: " + Long.toString(usuario.getId()));
      assertNotNull(usuario.getId());
      assertEquals("Juan", getNombreFromUsuarioDB(usuario.getId()));
   }

   private String getNombreFromUsuarioDB(Long usuarioId) {
      String nombre = db.withConnection(connection -> {
         String selectStatement = "SELECT Nombre FROM Usuario WHERE id = ? ";
         PreparedStatement prepStmt = connection.prepareStatement(selectStatement);
         prepStmt.setLong(1, usuarioId);
         ResultSet rs = prepStmt.executeQuery();
         rs.next();
         return rs.getString("Nombre");
      });
      return nombre;
   }
}
