package Clinica.proyecto.service;

import Clinica.proyecto.domain.Tratamiento;
import java.util.List;

public interface TratamientoService {

    public List<Tratamiento> getTratamientos();

    public Tratamiento getTratamiento(Tratamiento tratamiento);

    public void save(Tratamiento tratamiento, boolean accion, Long id_tratamiento);

    public void delete(Tratamiento tratamiento);

}
