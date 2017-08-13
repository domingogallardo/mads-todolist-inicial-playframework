package controllers;

import play.data.validation.Constraints;

// Usamos los constraints para que se validen automáticamente
// en el formulario: https://www.playframework.com/documentation/2.5.x/JavaForms
public class Registro {
   @Constraints.Required
   public String login;
   @Constraints.Required
   public String email;
   @Constraints.Required
   public String password;
   @Constraints.Required
   public String confirmacion;
}
