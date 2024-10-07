package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Medicamentos;
import static com.github.mustachejava.util.NodeValue.value;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicamentosDao extends JpaRepository<Medicamentos, Long> {

    @Query(value = "CALL ingresoMedicamento(:nombre,:descripcion, :indicaciones, :efectos, :fecha, :costo, :dosis)", nativeQuery = true)
    public void ingresoMedicamento(
            @Param("nombre") String nombre,
            @Param("descripcion") String descripcion,
            @Param("indicaciones") String indicaciones,
            @Param("efectos") String efectos_secundarios,
            @Param("fecha") String fecha_caducidad,
            @Param("costo") double costo,
            @Param("dosis") String dosis
    );
    
    
    
       @Query(value = "CALL obtenerMedicamento(:codigo)", nativeQuery = true)
    public void obtenerMedicamento(
            @Param("codigo") Long codigo_medicamento
           
     );
    
    
    @Query(value = "CALL actualizarMedicamento(:p_codigo_medicamento ,:p_nombre,:p_descripcion, :p_indicaciones, :p_efectos, :p_fecha, :p_costo, :p_dosis)", nativeQuery = true)
    public void actualizarMedicamento(
             @Param("p_codigo_medicamento") Long codigo_medicamento,
            @Param("p_nombre") String nombre,
            @Param("p_descripcion") String descripcion,
            @Param("p_indicaciones") String indicaciones,
            @Param("p_efectos") String efectos_secundarios,
            @Param("p_fecha") String fecha_caducidad,
            @Param("p_costo") double costo,
            @Param("p_dosis") String dosis
    );
    
    
     @Query(value = "CALL eliminarMedicamento(:p_codigo_medicamento)", nativeQuery = true)
    public void eliminarMedicamento(
             @Param("p_codigo_medicamento") Long codigo_medicamento);
      
}
