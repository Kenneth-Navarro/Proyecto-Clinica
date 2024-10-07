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
@Table(name = "tratamiento")
public class Tratamiento implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Tratamiento")
    private Long ID_Tratamiento;
    private String nombre;
    private String duracion;
    private double costo;

    public Tratamiento() {
    }

    public Tratamiento(Long ID_Tratamiento, String nombre, String duracion, double costo) {
        this.ID_Tratamiento = ID_Tratamiento;
        this.nombre = nombre;
        this.duracion = duracion;
        this.costo = costo;
    }



}
