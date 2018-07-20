package controllers;

import models.Equipo;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.EquipoService;

// Es necesario importar las vistas que se van a usar
import views.html.formNuevoEquipo;
import views.html.listaEquipos;
import views.html.formEquipoUsuario;

import javax.inject.Inject;
import java.util.List;

public class EquipoController extends Controller {
    @Inject
    FormFactory formFactory;
    @Inject
    EquipoService equipoService;

    public Result formularioNuevoEquipo() {
        return ok(formNuevoEquipo.render(""));
    }

    public Result creaNuevoEquipo() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String nombre = requestData.get("nombre");
        if (nombre == null || nombre.equals("")) {
            return badRequest(formNuevoEquipo.render("Debes rellenar el nombre"));
        }
        equipoService.addEquipo(nombre);
        return ok("Equipo " + nombre + " añadido correctamente");
    }

    public Result listaEquipos() {
        List<Equipo> equipos = equipoService.allEquipos();
        return ok(listaEquipos.render(equipos));
    }

    public Result formularioAddUsuarioEquipo() {
        return ok(formEquipoUsuario.render());
    }

    public Result addUsuarioEquipo() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String equipo = requestData.get("equipo");
        String usuario = requestData.get("usuario");
        equipoService.addUsuarioEquipo(usuario, equipo);
        return ok("Usuario " + usuario + " añadido al equipo " + equipo);
    }
}
