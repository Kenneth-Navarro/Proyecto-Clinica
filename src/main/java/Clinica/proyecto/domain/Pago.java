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
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Pago")
    private Long idPago;
    private double monto;
    private LocalDate fecha;
    private String metodoPago;
    private String estadoPago;
    private String detalle;

    public Pago() {
    }

    public Pago(double monto, LocalDate fecha, String metodoPago, String estadoPago, String detalle) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.estadoPago = estadoPago;
        this.detalle = detalle;

    }

    @OneToOne
    @JoinColumn(name = "ID_Paciente")
    Paciente paciente;


}
