package Clinica.proyecto.service;

import Clinica.proyecto.domain.Pago;
import java.util.List;

public interface PagoService {

    public List<Pago> getPagos();

    public Pago getPago(Pago pago);

    public void save(Pago pago, boolean accion, Long ID);

    public void delete(Pago pago);
    
    public void actualizaEstado();
    
    
}
