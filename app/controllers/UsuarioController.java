package controllers;

import play.mvc.*;

import views.html.*;
import javax.inject.*;
import play.data.Form;
import play.data.FormFactory;

import services.UsuarioService;
import models.Usuario;


public class UsuarioController extends Controller {

   @Inject FormFactory formFactory;

   // Play injecta un usuarioService junto con todas las dependencias necesarias:
   // UsuarioRepository y JPAApi
   @Inject UsuarioService usuarioService;

   public Result saludo(String mensaje) {
      return ok(saludo.render("El mensaje que he recibido es: " + mensaje));
   }

   public Result formularioRegistro() {
      return ok(formRegistro.render(formFactory.form(Registro.class),""));
   }

   public Result registroUsuario() {
      Form<Registro> form = formFactory.form(Registro.class).bindFromRequest();
      if (form.hasErrors()) {
         return badRequest(formRegistro.render(form, "Hay errores en el formulario"));
      }
      Registro datosRegistro = form.get();
      Usuario usuario = usuarioService.creaUsuario(datosRegistro.login, datosRegistro.email, datosRegistro.password);
      return ok(saludo.render("Creado " + usuario.toString()));
   }
}
