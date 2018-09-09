package controllers;

import play.data.validation.Constraints;

// Usamos los constraints para que se validen automáticamente
// en el formulario: https://www.playframework.com/documentation/2.5.x/JavaForms
public class Registro {
    @Constraints.Required
    public String username;
    @Constraints.Required
    @Constraints.Email
    public String email;
    @Constraints.Required
    public String password;
    @Constraints.Required
    public String confirmacion;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }
}
