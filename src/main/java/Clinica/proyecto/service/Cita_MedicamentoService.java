package Clinica.proyecto.service;

import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Cita_Medicamento;
import Clinica.proyecto.domain.Medicamentos;
import java.util.List;

public interface Cita_MedicamentoService {

    public List<Medicamentos> getCitasMedicamentos(Long idCita);

    public void save(Cita cita, Medicamentos medicamento);

    public void delete(Cita cita);

}
