package Clinica.proyecto.dao;

import Clinica.proyecto.domain.TipoCita;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TipoCitaDao extends JpaRepository<TipoCita, Long> {
    
    
    
}