package Clinica.proyecto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    @Id
    @Column(name = "cedula")
    private long cedula;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String telefono;
    private String provincia;
    private String canton;
    private String distrito;
    private String otros;
    private int edad;
    private boolean genero;

    public Paciente() {
    }

    public Paciente(long cedula, String nombre, String primerApellido, String segundoApellido, String correo, String telefono, String provincia, String canton, String distrito, String otros, int edad, boolean genero) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correo = correo;
        this.telefono = telefono;
        this.provincia = provincia;
        this.canton = canton;
        this.distrito = distrito;
        this.otros = otros;
        this.edad = edad;
        this.genero = genero;
    }

}
