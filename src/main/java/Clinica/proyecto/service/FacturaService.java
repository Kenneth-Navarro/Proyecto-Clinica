package Clinica.proyecto.service;

import Clinica.proyecto.domain.Factura;
import java.util.List;

public interface FacturaService {

    public List<Factura> getFacturas();

    public Factura getFactura(Factura factura);

    public void save(Factura factura, boolean accion,Long ID);

    public void delete(Factura factura);

}
