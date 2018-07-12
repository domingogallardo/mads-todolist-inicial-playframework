package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import javax.persistence.*;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String email;
    private String password;
    private String nombre;
    private String apellidos;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    // Relación uno-a-muchos entre usuario y tarea
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    public Set<Tarea> tareas = new HashSet<Tarea>();

    // Un constructor vacío necesario para JPA
    public Usuario() {
    }

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

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }

    public String toString() {
        String fechaStr = null;
        if (fechaNacimiento != null) {
            SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
            fechaStr = formateador.format(fechaNacimiento);
        }
        return String.format("Usuario id: %s login: %s password: %s nombre: %s " +
                        "apellidos: %s e-mail: %s fechaNacimiento: %s",
                id, login, password, nombre, apellidos, email, fechaNacimiento);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Usuario other = (Usuario) obj;
        // Si tenemos los ID, comparamos por ID
        if (id != null && other.id != null)
            return (id == other.id);
            // sino comparamos por campos obligatorios
        else {
            if (login == null) {
                if (other.login != null) return false;
            } else if (!login.equals(other.login)) return false;
            if (email == null) {
                if (other.email != null) return false;
            } else if (!email.equals(other.email)) return false;
        }
        return true;
    }
}
