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
@Table(name = "medicamento")
public class Medicamentos implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_medicamento")
    private Long codigo_medicamento;
    private String nombre;
    private String descripcion;
    private String indicaciones;
    private String efectos_secundarios;
    private String fecha_caducidad;
    private double costo;
    private String dosis;


    public Medicamentos() {
    }

    public Medicamentos(Long codigo_medicamento, String nombre, String descripcion, String indicaciones, String efectos_secundarios, String fecha_caducidad, double costo, String dosis) {
        this.codigo_medicamento = codigo_medicamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.indicaciones = indicaciones;
        this.efectos_secundarios = efectos_secundarios;
        this.fecha_caducidad = fecha_caducidad;
        this.costo = costo;
        this.dosis = dosis;
    }

    

}
