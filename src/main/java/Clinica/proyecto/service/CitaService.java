package Clinica.proyecto.service;

import Clinica.proyecto.domain.Cita;
import java.util.List;

public interface CitaService {

    public List<Cita> getCitas();

    public Cita getCita(Cita cita);

    public void save(Cita cita, boolean acccion);

    public void delete(Cita cita);
    
    public void actualizaEstado(Long idcita);
    
    

}
