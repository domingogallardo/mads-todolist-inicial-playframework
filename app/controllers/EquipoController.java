package controllers;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.EquipoService;

// Es necesario importar las vistas que se van a usar
import views.html.formNuevoEquipo;

import javax.inject.Inject;

public class EquipoController extends Controller {
    @Inject
    FormFactory formFactory;
    @Inject
    EquipoService equipoService;

    public Result formularioNuevoEquipo() {
        return ok(formNuevoEquipo.render());
    }

    public Result creaNuevoEquipo() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String nombre = requestData.get("nombre");
        equipoService.addEquipo(nombre);
        return ok("Equipo añadido correctamente");
    }
}
