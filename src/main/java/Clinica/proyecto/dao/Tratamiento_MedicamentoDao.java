package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Tratamiento;
import Clinica.proyecto.domain.Tratamiento_Medicamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface Tratamiento_MedicamentoDao extends JpaRepository<Tratamiento_Medicamento,Long> {
     @Query(value = "CALL ingresarTratamiento_medicamento(:id_trata, :id_medi)", nativeQuery = true)
    public void ingresarTratamiento_medicamento(@Param("id_trata") long ID_Tratamiento, @Param("id_medi") long ID_Medicamento);
       
    @Query(value = "CALL eliminarTratamiento_Medicamento(:id_trata)", nativeQuery=true)
    public void eliminarTratamiento_Medicamento(@Param("id_trata") long ID_Tratamiento);
   
}