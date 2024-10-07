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
@Table(name = "Tratamiento_Medicamento")
public class Tratamiento_Medicamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTM")
    private Long idTM;

    @Column(name = "ID_Tratamiento")
    private Long idTratamiento; 

    @Column(name = "ID_Medicamento")
    private Long idMedicamento;

    
}

