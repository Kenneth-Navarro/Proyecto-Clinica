package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Cita_Medicamento;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface Cita_MedicamentoDao extends JpaRepository<Cita_Medicamento, Long> {

    
    @Query(value = "CALL ingresoMedicamentoCita(:idCita, :idMedicamento)", nativeQuery = true)
    public void ingresaMedicamentoCita(@Param("idCita")Long idCita, @Param("idMedicamento") Long idMedicamento);
 
    @Query(value = "CALL eliminaMedicamentoCita(:idCita)", nativeQuery=true)
    public void eliminarMedicamentoCita(@Param("idCita") Long idCita);
}