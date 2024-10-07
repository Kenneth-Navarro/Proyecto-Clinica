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
@Table(name = "cita")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cita")
    private Long idCita;
    private LocalDate Fecha;
    private String motivoCita;
    private String Estado;

    public Cita() {
    }

    public Cita(LocalDate Fecha, String motivoCita, String Estado) {
        this.Fecha = Fecha;
        this.motivoCita = motivoCita;
        this.Estado = Estado;
        
    }

    @OneToOne
    @JoinColumn(name = "Id_Tipo_Cita")
    TipoCita tipo;
    
    @OneToOne
    @JoinColumn(name = "Cedula_Medico")
    Medico medico;
    
    @OneToOne
    @JoinColumn(name = "Cedula_Paciente")
    Paciente paciente;
    

}
