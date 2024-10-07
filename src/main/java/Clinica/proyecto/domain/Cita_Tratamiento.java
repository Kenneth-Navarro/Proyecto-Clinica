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
@Table(name = "Cita_Tratamiento")
public class Cita_Tratamiento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CITA_TRATAMIENTO")
    private Long idCitaMedicamento;
    
    @Column(name = "ID_TRATAMIENTO")
    private Long idTratamiento; // ID del tratamiento

    @Column(name = "ID_CITA")
    private Long idCita; // ID de
    
   
    
}

