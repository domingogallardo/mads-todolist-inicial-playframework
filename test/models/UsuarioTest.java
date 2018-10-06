package models;

import org.dbunit.JndiDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Environment;
import play.Logger;
import play.db.Database;
import play.db.jpa.JPAApi;
import play.inject.Injector;
import play.inject.guice.GuiceApplicationBuilder;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class UsuarioTest {
    static Database db;
    static private Injector injector;

    // Se ejecuta sólo una vez, al principio de todos los tests
    @BeforeClass
    static public void initApplication() {
        GuiceApplicationBuilder guiceApplicationBuilder =
                new GuiceApplicationBuilder().in(Environment.simple());
        injector = guiceApplicationBuilder.injector();
        // Obtenemos la base de datos utilizada por la aplicación
        db = injector.instanceOf(Database.class);
        // Necesario para inicializar JPA
        injector.instanceOf(JPAApi.class);
    }


    // Se ejecuta al antes de cada test
    // Se insertan los datos de prueba en la tabla Usuarios de
    // la BD "DBTodoList". La BD ya contiene una tabla de usuarios
    // porque la ha creado JPA al tener la propiedad
    // hibernate.hbm2ddl.auto (en META-INF/persistence.xml) el valor update
    // Los datos de prueba se definen en el fichero
    // test/resources/test_dataset.xml
    @Before
    public void initData() throws Exception {
        // Creamos la base de datos de test y le asignamos el nombre JNDI DBTodoList
        JndiDatabaseTester databaseTester = new JndiDatabaseTester("DBTodoList");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new
                FileInputStream("test/resources/test_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        // Definimos como operación SetUp CLEAN_INSERT, que hace un
        // DELETE_ALL de todas las tablase del dataset, seguido por un
        // INSERT. (http://dbunit.sourceforge.net/components.html)
        // Es lo que hace DbUnit por defecto, pero así queda más claro.
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();

    }

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

    @Test
    public void testAddUsuarioJPARepositoryInsertsUsuarioDatabase() {
        UsuarioRepository repository = injector.instanceOf(UsuarioRepository.class);
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
            String selectStatement = "SELECT NOMBRE FROM Usuario WHERE ID = ? ";
            PreparedStatement prepStmt = connection.prepareStatement(selectStatement);
            prepStmt.setLong(1, usuarioId);
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            return rs.getString("NOMBRE");
        });
        return nombre;
    }

    @Test
    public void testFindUsuarioPorId() {
        UsuarioRepository repository = injector.instanceOf(UsuarioRepository.class);
        Usuario usuario = repository.findById(1000L);
        assertEquals("juangutierrez", usuario.getLogin());
    }

    @Test
    public void testFindUsuarioPorLogin() {
        UsuarioRepository repository = injector.instanceOf(UsuarioRepository.class);
        Usuario usuario = repository.findByLogin("juangutierrez");
        assertEquals((Long) 1000L, usuario.getId());
    }

    @Test
    public void testEqualsUsuariosConId() {
        Usuario usuario1 = new Usuario("juangutierrez", "juangutierrez@gmail.com");
        Usuario usuario2 = new Usuario("mariafernandez", "mariafernandez@gmail.com");
        Usuario usuario3 = new Usuario("antoniolopez", "antoniolopez@gmail.com");
        usuario1.setId(1001L);
        usuario2.setId(1001L);
        usuario3.setId(1002L);
        assertEquals(usuario1, usuario2);
        assertNotEquals(usuario1, usuario3);
    }

    @Test
    public void testEqualsUsuariosSinId() {
        Usuario usuario1 = new Usuario("mariafernandez", "mariafernandez@gmail.com");
        Usuario usuario2 = new Usuario("mariafernandez", "mariafernandez@gmail.com");
        Usuario usuario3 = new Usuario("antoniolopez", "antoniolopez@gmail.com");
        assertEquals(usuario1, usuario2);
        assertNotEquals(usuario1, usuario3);
    }
}
