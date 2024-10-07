package Clinica.proyecto.service;

import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Cita_Medicamento;
import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Tratamiento;
import java.util.List;

public interface Cita_TratamientoService {

    public List<Tratamiento> getCitaTratamientos(Long idCita);

    public void save(Cita cita, Tratamiento tratamiento);

    public void delete(Cita cita);

}
