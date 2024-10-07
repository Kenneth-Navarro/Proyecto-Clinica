package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PacienteDao extends JpaRepository<Paciente, Long> {

    @Query(value = "CALL ingresarPaciente(:ced, :nombre, :primerApellido, :segundoApellido, :telefono, :correo, :provincia, :canton, :distrito, :otros, :edad, :genero)", nativeQuery = true)
    public void ingresarPaciente(
            @Param("ced") long cedula,
            @Param("nombre") String nombre,
            @Param("primerApellido") String primerApellido,
            @Param("segundoApellido") String segundoApellido,
            @Param("telefono") String telefono,
            @Param("correo") String correo,
            @Param("provincia") String provincia,
            @Param("distrito") String distrito,
            @Param("canton") String canton,
            @Param("otros") String otros,
            @Param("edad") int edad,
            @Param("genero") boolean genero);

    @Query(value = "CALL actualizarPaciente(:Cedu, :Nomb, :Primerap, :Segundoap, :Tel, :Cor, :Prov, :Cant, :Dist, :Otr, :Eda, :Gen, :cedID)", nativeQuery = true)
    public void actualizarPaciente(
            @Param("Cedu") long cedula,
            @Param("Nomb") String nombre,
            @Param("Primerap") String primerApellido,
            @Param("Segundoap") String segundoApellido,
            @Param("Tel") String telefono,
            @Param("Cor") String correo,
            @Param("Prov") String provincia,
            @Param("Dist") String distrito,
            @Param("Cant") String canton,
            @Param("Otr") String otros,
            @Param("Eda") int edad,
            @Param("Gen") boolean genero,
            @Param("cedID") long cedID);

    @Query(value = "CALL eliminarPaciente(:CED)", nativeQuery = true)
    public void eliminarPaciente(@Param("CED") Long CED);

}
