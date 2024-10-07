package Clinica.proyecto.service;

import Clinica.proyecto.domain.Medico;
import java.util.List;

public interface MedicoService {

    public List<Medico> getMedicos();

    public Medico getMedico(Medico medico);

    public void save(Medico medico, boolean accion, Long ced);

    public void delete(Medico medico);

}
