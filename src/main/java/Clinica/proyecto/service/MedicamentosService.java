package Clinica.proyecto.service;

import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Medico;
import java.util.List;

public interface MedicamentosService {

    public List<Medicamentos> getMedicamentos();

    public Medicamentos getMedicamentos(Medicamentos medicamentos);

    public void save(Medicamentos medicamentos);
    
     public void update(Medicamentos medicamentos);

    public void delete(Medicamentos medicamentos);

}
