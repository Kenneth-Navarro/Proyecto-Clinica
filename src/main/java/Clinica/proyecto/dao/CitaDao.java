package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Cita;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CitaDao extends JpaRepository<Cita, Long> {
    
    @Query(value = "CALL ingresaCita(:CedMedico, :CedPaciente, :idTipoCita, :fecha, :motivo, :estado)", nativeQuery = true)
    public void ingresaCita(@Param("CedMedico")Long CedMedico, @Param("CedPaciente") Long cedPaciente, @Param("idTipoCita")Long idTipoCita, @Param("fecha") 
            LocalDate fecha, @Param("motivo") String motivo, @Param("estado") String estado);
    
    
     @Query(value = "CALL actualizaCita(:CedMedico, :CedPaciente, :idTipoCita, :fech, :motivo, :estad, :idCit)", nativeQuery = true)
    public void actualizaCita(@Param("CedMedico")Long CedMedico, @Param("CedPaciente") Long cedPaciente, @Param("idTipoCita")Long idTipoCita, @Param("fech") 
            LocalDate fecha, @Param("motivo") String motivo, @Param("estad") String estado, @Param("idCit") Long idCit);
    
     @Query(value = "CALL actualizaEstadoCita(:idCita)", nativeQuery = true)
    public void actualizaEstadoCita(@Param("idCita")Long idCita);
}