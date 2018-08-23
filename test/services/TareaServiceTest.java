package services;

import models.Tarea;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TareaServiceTest {
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

    @Test
    public void allTareasUsuarioEstanOrdenadas() {
        TareaService tareaService = injector.instanceOf(TareaService.class);
        List<Tarea> tareas = tareaService.allTareasUsuario(1000L);
        assertEquals("Renovar DNI", tareas.get(0).getTitulo());
        assertEquals("Práctica 1 MADS", tareas.get(1).getTitulo());
    }

    @Test(expected = TareaServiceException.class)
    public void crearNuevoUsuarioLoginRepetidoLanzaExcepcion() {
        TareaService tareaService = injector.instanceOf(TareaService.class);
        List<Tarea> tareas = tareaService.allTareasUsuario(1001L);
    }

    @Test
    public void nuevaTareaUsuario() {
        TareaService tareaService = injector.instanceOf(TareaService.class);
        long idUsuario = 1000L;
        Tarea tarea = tareaService.nuevaTarea(idUsuario, "Pagar el alquiler");
        // El usuario en la tarea devuelta ya tiene actualizada la lista de tareas
        assertEquals(3, tarea.getUsuario().getTareas().size());
        // El método allTareasUsuario devuelve correctamente la lista actualizada de tareas
        assertEquals(3, tareaService.allTareasUsuario(1000L).size());
    }

    @Test
    public void modificacionTarea() {
        TareaService tareaService = injector.instanceOf(TareaService.class);
        long idTarea = 1001L;
        tareaService.modificaTarea(idTarea, "Pagar el alquiler");
        Tarea tarea = tareaService.obtenerTarea(idTarea);
        assertEquals("Pagar el alquiler", tarea.getTitulo());
    }

    @Test
    public void borradoTarea() {
        TareaService tareaService = injector.instanceOf(TareaService.class);
        long idTarea = 1001L;
        tareaService.borraTarea(idTarea);
        assertNull(tareaService.obtenerTarea(idTarea));
        // Comprobamos también que la tarea se ha eliminado también de la lista de tareas
        // recuperadas del usuario
        assertEquals(1, tareaService.allTareasUsuario(1000L).size());
    }

    @Test
    public void cambiaUsuarioTarea() {
        TareaService tareaService = injector.instanceOf(TareaService.class);
        tareaService.cambiaUsuarioTarea(1001L, 1005L);
        assertEquals(1, tareaService.allTareasUsuario(1000L).size());
        assertEquals(1, tareaService.allTareasUsuario(1005L).size());
    }
}
