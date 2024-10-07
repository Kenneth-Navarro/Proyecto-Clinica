package Clinica.proyecto.service;

import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Paciente;
import Clinica.proyecto.domain.Tratamiento;
import java.util.List;

public interface PacienteService {

    public List<Paciente> getPacientes();
    
    public Paciente getPaciente(Paciente paciente);

    public void save(Paciente paciente, boolean accion, Long ced);
    
    public void delete(Paciente paciente);
    
    public List<Cita> getCitas(Paciente paciente);
    
    public Tratamiento getTratamiento(Tratamiento tratamiento);
    
    public List<Tratamiento> getTratamientos(List<Cita> cita);
    
    public Medicamentos getMedicamento(Medicamentos medicamento);
    
    public List<Medicamentos> getMedicamentos(List<Cita> cita);
    
    

}
