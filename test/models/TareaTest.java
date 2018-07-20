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

import static org.junit.Assert.*;

public class TareaTest {
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

    @Before
    public void initData() throws Exception {
        // Creamos la base de datos de test y le asignamos el nombre JNDI DBTodoList
        JndiDatabaseTester databaseTester = new JndiDatabaseTester("DBTodoList");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("test/resources/usuarios_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    private UsuarioRepository newUsuarioRepository() {
        return injector.instanceOf(UsuarioRepository.class);
    }

    @Test
    public void testCrearTarea() {
        Usuario usuario = new Usuario("juangutierrez", "juangutierrez@gmail.com");
        Tarea tarea = new Tarea(usuario, "Práctica 1 de MADS");

        assertEquals("juangutierrez", tarea.getUsuario().getLogin());
        assertEquals("juangutierrez@gmail.com", tarea.getUsuario().getEmail());
        assertEquals("Práctica 1 de MADS", tarea.getTitulo());
    }

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

    @Test
    public void testEqualsTareasSinId() {
        Usuario usuario = new Usuario("juangutierrez", "juangutierrez@gmail.com");
        Tarea tarea1 = new Tarea(usuario, "Renovar DNI");
        Tarea tarea2 = new Tarea(usuario, "Renovar DNI");
        Tarea tarea3 = new Tarea(usuario, "Pagar el alquiler");
        assertEquals(tarea1, tarea2);
        assertNotEquals(tarea1, tarea3);
    }

    @Test
    public void testAddTareaJPARepositoryInsertsTareaDatabase() {
        UsuarioRepository usuarioRepository = injector.instanceOf(UsuarioRepository.class);
        TareaRepository tareaRepository = injector.instanceOf(TareaRepository.class);
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
            String selectStatement = "SELECT TITULO FROM Tarea WHERE ID = ? ";
            PreparedStatement prepStmt = connection.prepareStatement(selectStatement);
            prepStmt.setLong(1, tareaId);
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            return rs.getString("TITULO");
        });
        return titulo;
    }

    @Test
    public void testFindTareaPorId() {
        TareaRepository repository = injector.instanceOf(TareaRepository.class);
        Tarea tarea = repository.findById(1001L);
        assertEquals("Renovar DNI", tarea.getTitulo());
    }

    @Test
    public void testFindAllTareasUsuario() {
        UsuarioRepository repository = injector.instanceOf(UsuarioRepository.class);
        Usuario usuario = repository.findById(1000L);
        assertEquals(2, usuario.getTareas().size());
    }
}
