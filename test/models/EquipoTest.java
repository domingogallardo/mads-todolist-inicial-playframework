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
import java.util.List;

import static org.junit.Assert.*;

public class EquipoTest {
    static Database db;
    static private Injector injector;

    // Se ejecuta sólo una vez, al principio de todos los tests
    @BeforeClass
    static public void initApplication() {
        // Creamos la aplicación a partir del fichero de configuración.
        // El fichero de configuración se puede cambiar en el comando
        // para lanzar sbt y los tests:
        // sbt '; set javaOptions += "-Dconfig.file=conf/develop-mysql.conf"; testOnly Integration*'
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
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("test/resources/test_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @Test
    public void testEqualsEquiposConId() {
        Equipo equipo1 = new Equipo("Equipo A");
        Equipo equipo2 = new Equipo("Equipo B");
        Equipo equipo3 = new Equipo("Equipo C");
        equipo1.setId(1L);
        equipo2.setId(1L);
        equipo3.setId(2L);
        assertEquals(equipo1, equipo2);
        assertNotEquals(equipo1, equipo3);
    }

    @Test
    public void testEqualsEquiposSinId() {
        Equipo equipo1 = new Equipo("Equipo A");
        Equipo equipo2 = new Equipo("Equipo A");
        Equipo equipo3 = new Equipo("Equipo B");
        assertEquals(equipo1, equipo2);
        assertNotEquals(equipo1, equipo3);
    }

    @Test
    public void testAddEquipoJPARepositoryInsertsEquipoDatabase() {
        EquipoRepository equipoRepository = injector.instanceOf(EquipoRepository.class);
        Equipo equipo = new Equipo("Equipo A");
        equipo = equipoRepository.add(equipo);
        Logger.info("Número de tarea: " + Long.toString(equipo.getId()));
        assertNotNull(equipo.getId());
        assertEquals("Equipo A", getNombreFromEquipoDB(equipo.getId()));
    }

    private String getNombreFromEquipoDB(Long equipoId) {
        String titulo = db.withConnection(connection -> {
            String selectStatement = "SELECT NOMBRE FROM Equipo WHERE ID = ? ";
            PreparedStatement prepStmt = connection.prepareStatement(selectStatement);
            prepStmt.setLong(1, equipoId);
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            return rs.getString("NOMBRE");
        });
        return titulo;
    }

    @Test
    public void testFindEquipoPorId() {
        EquipoRepository repository = injector.instanceOf(EquipoRepository.class);
        Equipo equipo = repository.findById(1003L);
        assertEquals("Equipo A", equipo.getNombre());
    }

    @Test
    public void testUpdateEquipo() {
        EquipoRepository repository = injector.instanceOf(EquipoRepository.class);
        Equipo equipo = new Equipo("Equipo B");
        equipo.setId(1003L);
        repository.update(equipo);
        Equipo equipoActualizado = repository.findById(1003L);
        assertEquals("Equipo B", equipo.getNombre());
    }

    @Test
    public void testDeleteEquipo() {
        EquipoRepository repository = injector.instanceOf(EquipoRepository.class);
        Equipo equipo = repository.findById(1003L);
        repository.delete(equipo);
        equipo = repository.findById(1003L);
        assertNull(equipo);
    }

    @Test
    public void testAddUsuarioEquipo() {
        EquipoRepository equipoRepository = injector.instanceOf(EquipoRepository.class);
        UsuarioRepository usuarioRepository = injector.instanceOf(UsuarioRepository.class);
        Equipo equipo = equipoRepository.findById(1005L);
        Usuario usuario = usuarioRepository.findById(1005L);
        equipoRepository.addUsuarioEquipo(usuario, equipo);

        // Recuperamos las entidades de la base de datos y comprobamos
        // que los datos se han actualizado
        List<Usuario> usuarios = equipoRepository.findUsuariosEquipo(equipo.getId());
        assertEquals(1, usuarios.size());
        Usuario usuarioBD = usuarioRepository.findById(1005L);
        assertEquals(2, usuarioBD.getEquipos().size());
    }
}
