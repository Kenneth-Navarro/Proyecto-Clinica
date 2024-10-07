package Clinica.proyecto.service;

import Clinica.proyecto.domain.Especialidad;
import java.util.List;

public interface EspecialidadService {

    public List<Especialidad> getEspecialidades();

    public List<Especialidad> getEspecialidad(Long ID);

    public void save(Especialidad especialidad);

    public void delete(Especialidad especialidad);

}
