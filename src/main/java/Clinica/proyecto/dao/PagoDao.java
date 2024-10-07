package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Pago;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PagoDao extends JpaRepository<Pago, Long> {

    @Query(value = "CALL ingresoPago(:idPaciente, :monto, :fecha, :metPago, :estPago, :detalle)", nativeQuery = true)
    public void ingresoPago(@Param("idPaciente") Long idPaciente, @Param("monto") double monto, @Param("fecha") LocalDate fecha, @Param("metPago") String metPago,
            @Param("estPago") String estPago, @Param("detalle") String detalle);


    @Query(value = "CALL actualizaEstadoPago()", nativeQuery = true)
    public void actualizaEstadoPago();
    
    @Query(value = "CALL actualizarPago(:idPaciente, :monto, :fecha, :metPago, :estPago, :detalle, :identificador )", nativeQuery = true)
    public void actualizaPago(@Param("idPaciente") Long idPaciente, @Param("monto") double monto, @Param("fecha") LocalDate fecha, @Param("metPago") String metPago,
            @Param("estPago") String estPago, @Param("detalle") String detalle, @Param("identificador") Long identificador);
    
    @Query(value = "CALL eliminarPago(:idPago)", nativeQuery=true)
    public void eliminarPago(
            @Param("idPago") Long idPago);
    }