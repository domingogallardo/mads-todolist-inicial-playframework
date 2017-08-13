import org.junit.*;
import static org.junit.Assert.*;

import play.db.Database;
import play.db.Databases;

import play.db.jpa.*;

import models.Usuario;
import models.UsuarioRepository;
import models.JPAUsuarioRepository;

import services.UsuarioService;
import services.UsuarioServiceException;


public class UsuarioServiceTest {
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

   //Test 5: crearNuevoUsuarioCorrectoTest
   @Test
   public void crearNuevoUsuarioCorrectoTest(){
      UsuarioRepository repository = new JPAUsuarioRepository(jpaApi);
      UsuarioService usuarioService = new UsuarioService(repository);
      Usuario usuario = usuarioService.creaUsuario("luciaruiz", "lucia.ruiz@gmail.com", "123456");
      assertNotNull(usuario.getId());
      assertEquals("luciaruiz", usuario.getLogin());
      assertEquals("lucia.ruiz@gmail.com", usuario.getEmail());
      assertEquals("123456", usuario.getPassword());
   }

   //Test 6: crearNuevoUsuarioLoginRepetidoLanzaExcepcion
   @Test(expected = UsuarioServiceException.class)
   public void crearNuevoUsuarioLoginRepetidoLanzaExcepcion(){
      UsuarioRepository repository = new JPAUsuarioRepository(jpaApi);
      UsuarioService usuarioService = new UsuarioService(repository);
      // En la BD de prueba usuarios_dataset se ha cargado el usuario juangutierrez
      Usuario usuario = usuarioService.creaUsuario("juangutierrez", "juan.gutierrez@gmail.com", "123456");
   }

   //Test 7: findUsuarioPorId
   @Test
   public void findUsuarioPorId() {
      UsuarioRepository repository = new JPAUsuarioRepository(jpaApi);
      UsuarioService usuarioService = new UsuarioService(repository);
      // En la BD de prueba usuarios_dataset se ha cargado el usuario juangutierrez
      Usuario usuario = usuarioService.findUsuarioPorLogin("juangutierrez");
      assertNotNull(usuario);
      assertEquals((Long) 1000L, usuario.getId());
   }
}
