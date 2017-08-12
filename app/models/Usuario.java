package models;

public class Usuario {
   public String login;
   public String email;
   public String password;
   public String nombre;

   public Usuario(String login, String email) {
      this.login = login;
      this.email = email;
   }
}
