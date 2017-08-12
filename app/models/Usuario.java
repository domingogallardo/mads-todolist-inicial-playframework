package models;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Usuario {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   private String login;
   private String email;
   private String password;
   private String nombre;
   private String apellidos;
   @Temporal(TemporalType.DATE)
   private Date fechaNacimiento;

   // Un constructor vacío necesario para JPA
   public Usuario() {}

   // El constructor principal con los campos obligatorios
   public Usuario(String login, String email) {
      this.login = login;
      this.email = email;
   }

   // Getters y setters necesarios para JPA

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getApellidos() {
      return apellidos;
   }

   public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
   }

   public Date getFechaNacimiento() {
      return fechaNacimiento;
   }

   public void setFechaNacimiento(Date fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
   }
}
