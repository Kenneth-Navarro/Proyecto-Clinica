package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Tratamiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TratamientoDao extends JpaRepository<Tratamiento, Long> {
    @Query(value = "CALL ingresarTratamiento(:Nombre, :Duracion, :Costo)", nativeQuery = true)
    public void ingresoTratamiento(@Param("Nombre") String Nombre, @Param("Duracion") String Duracion, @Param("Costo") double Costo);
    
    @Query(value = "CALL actualizarTratamiento(:Nomb, :Durac, :costoo, :id_trata)", nativeQuery = true)
    public void actualizarTratamiento(@Param("Nomb")  String Nomb, @Param("Durac")  String Durac, @Param("costoo") double costo, @Param("id_trata") Long id_trata);
    
    @Query(value = "CALL eliminarTratamiento(:id_trata)", nativeQuery=true)
    public void eliminarTratamiento(@Param("id_trata") Long id_trata);
}