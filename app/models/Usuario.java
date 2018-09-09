package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private Set<Tarea> tareas = new HashSet<>();
    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.EAGER)
    private Set<Equipo> equipos = new HashSet<>();

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

    public Set<Equipo> getEquipos() {
        return equipos;
    }

    // Función para imprimr los datos de un usuario
    public String toString() {
        String fechaStr = null;
        if (fechaNacimiento != null) {
            SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
            fechaStr = formateador.format(fechaNacimiento);
        }
        return String.format("Usuario id: %s username: %s password: %s nombre: %s " +
                        "apellidos: %s e-mail: %s fechaNacimiento: %s",
                id, login, password, nombre, apellidos, email, fechaNacimiento);
    }

    // Funciones hashCode y equals para poder comparar usuarios y
    // necesarias para poder crear un Set de usuarios
    @Override
    public int hashCode() {
        // Devolvemos el hash de los campos obligatorios
        return Objects.hash(login, email);
    }

    // Si el usuario tiene un ID (se ha obtenido de la BD)
    // la comparación se basa en ese ID. Si el ID no existe (el usuario
    // se ha creado en memoria y todavía no se ha sincronizado con la BD)
    // la comparación se basa en los atributos obligatorios.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Usuario other = (Usuario) obj;
        if (id != null && other.id != null) {
            // Si tenemos los ID, comparamos por ID
            return (id == other.id);
        }
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
