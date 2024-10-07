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
@Table(name = "facturas")
public class Factura implements Serializable {

    @Id
    @Column(name = "ID_Factura")
    private Long idFactura;

    public Factura() {
    }
    
    

    @OneToOne
    @JoinColumn(name = "ID_pago")
    Pago pago;


}
