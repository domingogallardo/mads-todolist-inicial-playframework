package models;

import java.util.Date;

public class Usuario {
   public String login;
   public String email;
   public String password;
   public String nombre;
   public String apellidos;
   public Date fechaNacimiento;

   public Usuario(String login, String email) {
      this.login = login;
      this.email = email;
   }
}
