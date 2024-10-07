package Clinica.proyecto.dao;

import Clinica.proyecto.domain.Medico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MedicoDao extends JpaRepository<Medico, Long> {
    
    @Query(value = "CALL ingresoMedico(:Ced, :Especial, :NumColegiado, :nom, :PrimerApellido, :SegundoApellido, :email, :tel, :prov, :cant, :distrit, :otr, :eda, :gen)", nativeQuery = true)
    public void ingresoMedico(@Param("Ced") Long Ced, @Param("Especial") Long Especial, @Param("NumColegiado") int NumColegiado, @Param("nom") String nom, @Param("PrimerApellido") String PrimerApellido,
            @Param("SegundoApellido") String SegundoApellido, @Param("email") String email, @Param("tel") String tel, @Param("prov") String prov, @Param("cant") String cant,
            @Param("distrit") String distrit, @Param("otr") String otr, @Param("eda") int eda, @Param("gen") boolean gen);
    
     @Query(value = "CALL actualizarMedico(:Ced, :Especial, :NumColegiado, :nom, :PrimerApellido, :SegundoApellido, :email, :tel, :prov, :cant, :distrit, :otr, :eda, :gen, :cedId)", nativeQuery = true)
    public void actualizaMedico(@Param("Ced") Long Ced, @Param("Especial") Long Especial, @Param("NumColegiado") int NumColegiado, @Param("nom") String nom, @Param("PrimerApellido") String PrimerApellido,
            @Param("SegundoApellido") String SegundoApellido, @Param("email") String email, @Param("tel") String tel, @Param("prov") String prov, @Param("cant") String cant,
            @Param("distrit") String distrit, @Param("otr") String otr, @Param("eda") int eda, @Param("gen") boolean gen, @Param("cedId") Long cedId);
    
    
    @Query(value = "CALL eliminarMedico(:CED)", nativeQuery=true)
    public void eliminarMedico(@Param("CED") Long CED);
    
}