package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Factura;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FacturaDao extends JpaRepository<Factura, Long> {
    
    
    @Query(value = "CALL ingresoFactura(:idPago)", nativeQuery = true)
    public void ingresoFactura(@Param("idPago") Long idPago);
    
    
    @Query(value = "CALL eliminarFactura(:idFactura)", nativeQuery = true)
    public void eliminarFactura(@Param("idFactura") Long idFactura);
    
    
    @Query(value = "CALL actualizarFactura(:idFactura, :idPago)", nativeQuery = true)
    public void actualizaFactura(@Param("idFactura") Long idFactura,@Param("idPago") Long idPago);
}