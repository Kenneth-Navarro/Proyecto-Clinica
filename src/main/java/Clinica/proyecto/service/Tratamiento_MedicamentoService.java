package Clinica.proyecto.service;


import Clinica.proyecto.domain.Tratamiento;
import Clinica.proyecto.domain.Tratamiento_Medicamento;
import java.util.List;

public interface Tratamiento_MedicamentoService {

    public List<Tratamiento_Medicamento> getTratamiento_Medicamentos(Tratamiento tratamiento);

    public Tratamiento_Medicamento getTratamiento_Medicamento(Tratamiento_Medicamento tratamiento_medicamento);

    public void save(long ID_Tratamiento, long ID_Medicamento);

    public void delete(long ID_Tratamiento);

}
