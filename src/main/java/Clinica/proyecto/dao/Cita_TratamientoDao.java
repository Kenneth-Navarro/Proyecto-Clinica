package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Cita_Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface Cita_TratamientoDao extends JpaRepository<Cita_Tratamiento, Long> {

    
    @Query(value = "CALL ingresoCitaTratamiento(:idCita, :idTratamiento)", nativeQuery = true)
    public void ingresaCitaTratamiento(@Param("idCita")Long idCita, @Param("idTratamiento") Long idTratamiento);
 
     @Query(value = "CALL eliminaCitaTratamiento(:idCita)", nativeQuery=true)
    public void eliminarTratamientooCita(@Param("idCita") Long idCita);
}