package controllers;

import models.Equipo;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.ActionAuthenticator;
import services.EquipoService;

// Es necesario importar las vistas que se van a usar
import services.EquipoServiceException;
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

    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioNuevoEquipo() {
        return ok(formNuevoEquipo.render(""));
    }

    @Security.Authenticated(ActionAuthenticator.class)
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

    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioAddUsuarioEquipo() {
        return ok(formEquipoUsuario.render());
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result addUsuarioEquipo() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        Long idEquipo = Long.parseLong(requestData.get("equipo"));
        Long idUsuario = Long.parseLong(requestData.get("usuario"));
        try {
            equipoService.addUsuarioEquipo(idUsuario, idEquipo);
            return ok("Usuario " + idUsuario + " añadido al equipo " + idEquipo);
        } catch (EquipoServiceException exception) {
            return notFound(exception.getMessage());
        }
    }
}
