package services;

import models.Usuario;
import org.dbunit.JndiDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Environment;
import play.db.jpa.JPAApi;
import play.inject.Injector;
import play.inject.guice.GuiceApplicationBuilder;
import services.UsuarioService;
import services.UsuarioServiceException;

import java.io.FileInputStream;

import static org.junit.Assert.*;

public class UsuarioServiceTest {
    static private Injector injector;

    // Se ejecuta sólo una vez, al principio de todos los tests
    @BeforeClass
    static public void initApplication() {
        GuiceApplicationBuilder guiceApplicationBuilder =
                new GuiceApplicationBuilder().in(Environment.simple());
        injector = guiceApplicationBuilder.injector();
        // Instanciamos un JPAApi para que inicializar JPA
        injector.instanceOf(JPAApi.class);
    }

    @Before
    public void initData() throws Exception {
        JndiDatabaseTester databaseTester = new JndiDatabaseTester("DBTodoList");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("test/resources/usuarios_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @Test
    public void crearNuevoUsuarioCorrectoTest() {
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        Usuario usuario = usuarioService.creaUsuario("luciaruiz", "lucia.ruiz@gmail.com", "123456");
        assertNotNull(usuario.getId());
        assertEquals("luciaruiz", usuario.getLogin());
        assertEquals("lucia.ruiz@gmail.com", usuario.getEmail());
        assertEquals("123456", usuario.getPassword());
    }

    @Test(expected = UsuarioServiceException.class)
    public void crearNuevoUsuarioLoginRepetidoLanzaExcepcion() {
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        // En la BD de prueba usuarios_dataset se ha cargado el usuario juangutierrez
        Usuario usuario = usuarioService.creaUsuario("juangutierrez", "juan.gutierrez@gmail.com", "123456");
    }

    @Test
    public void findUsuarioPorLogin() {
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        // En la BD de prueba usuarios_dataset se ha cargado el usuario juangutierrez
        Usuario usuario = usuarioService.findUsuarioPorLogin("juangutierrez");
        assertNotNull(usuario);
        assertEquals((Long) 1000L, usuario.getId());
    }


    @Test
    public void loginUsuarioExistenteTest() {
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        // En la BD de prueba usuarios_dataset se ha cargado el usuario juangutierrez
        Usuario usuario = usuarioService.login("juangutierrez", "123456789");
        assertEquals((Long) 1000L, usuario.getId());
    }

    @Test
    public void loginUsuarioNoExistenteTest() {
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        // En la BD de prueba usuarios_dataset se ha cargado el usuario juangutierrez
        Usuario usuario = usuarioService.login("juan", "123456789");
        assertNull(usuario);
    }

    @Test
    public void findUsuarioPorId() {
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        // En la BD de prueba usuarios_dataset se ha cargado el usuario juangutierrez
        Usuario usuario = usuarioService.findUsuarioPorId(1000L);
        assertNotNull(usuario);
        assertEquals("juangutierrez", usuario.getLogin());
    }
}
