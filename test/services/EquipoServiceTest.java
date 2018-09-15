package services;

import models.Equipo;
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

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EquipoServiceTest {
    static private Injector injector;

    @BeforeClass
    static public void initApplication() {
        GuiceApplicationBuilder guiceApplicationBuilder =
                new GuiceApplicationBuilder().in(Environment.simple());
        injector = guiceApplicationBuilder.injector();
        injector.instanceOf(JPAApi.class);
    }

    @Before
    public void initData() throws Exception {
        JndiDatabaseTester databaseTester = new JndiDatabaseTester("DBTodoList");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("test/resources/test_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @Test(expected = EquipoServiceException.class)
    public void addEquipoNombreRepetido() {
        EquipoService equipoService = injector.instanceOf(EquipoService.class);
        equipoService.addEquipo("Equipo A");
    }

    @Test
    public void listaEquipos() {
        EquipoService equipoService = injector.instanceOf(EquipoService.class);
        List<Equipo> equipos = equipoService.allEquipos();
        assertEquals(3, equipos.size());
    }

    @Test
    public void addUsuarioEquipo() {
        EquipoService equipoService = injector.instanceOf(EquipoService.class);
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        equipoService.addUsuarioEquipo("juangutierrez", "Equipo C");
        Usuario usuario = usuarioService.findUsuarioPorLogin("juangutierrez");
        List<Equipo> equipos = new ArrayList(usuario.getEquipos());
        assertEquals(3, equipos.size());
    }

    @Test
    public void getUsuariosEquipo() {
        EquipoService equipoService = injector.instanceOf(EquipoService.class);
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        List<Usuario> usuarios = equipoService.findUsuariosEquipo("Equipo A");
        assertEquals(2, usuarios.size());
        Usuario usuario = usuarioService.findUsuarioPorLogin("anagarcia");
        assertEquals(1, usuario.getEquipos().size());
    }

    @Test
    public void deleteUsuarioEquipo() {
        EquipoService equipoService = injector.instanceOf(EquipoService.class);
        UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
        equipoService.deleteUsuarioEquipo("juangutierrez", "Equipo A");
        List<Usuario> usuarios = equipoService.findUsuariosEquipo("Equipo A");
        assertEquals(1, usuarios.size());
        Usuario usuario = usuarioService.findUsuarioPorLogin("juangutierrez");
        assertEquals(1, usuario.getEquipos().size());
    }
}
