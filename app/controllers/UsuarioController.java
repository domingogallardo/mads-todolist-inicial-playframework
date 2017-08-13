package controllers;

import play.mvc.*;

import views.html.*;

public class UsuarioController extends Controller {

   public Result saludo(String mensaje) {
      return ok(saludo.render("El mensaje que he recibido es: " + mensaje));
   }
}
