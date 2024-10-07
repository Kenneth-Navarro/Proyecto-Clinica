package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.Cita_MedicamentoDao;
import Clinica.proyecto.dao.MedicamentosDao;
import Clinica.proyecto.dao.MedicoDao;
import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Cita_Medicamento;
import Clinica.proyecto.domain.Especialidad;
import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.service.Cita_MedicamentoService;
import Clinica.proyecto.service.MedicoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Cita_MedicamentoServiceImpl implements Cita_MedicamentoService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Cita_MedicamentoDao citaMedicamentoDao;

    @Autowired
    private MedicamentosServiceImpl medicamentosServiceImpl;

    @Override
    public List<Medicamentos> getCitasMedicamentos(Long idCita) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneMedicamentosdeCita");
        storedProcedure.registerStoredProcedureParameter("idCita", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        storedProcedure.setParameter("idCita", idCita);

        storedProcedure.execute();

        List<Object> resultados = storedProcedure.getResultList();
        List<Medicamentos> devueltos = new ArrayList<>();

        for (Object resultado : resultados) {
            BigDecimal IDMedicamento = (BigDecimal) resultado;
            Long idmedicamento = IDMedicamento.longValue();

            Medicamentos devuelto = new Medicamentos();
            devuelto.setCodigo_medicamento(idmedicamento);

            devuelto = medicamentosServiceImpl.getMedicamentos(devuelto);
            
            devueltos.add(devuelto);
        }

        return devueltos;
    }

    @Override
    public void save(Cita cita, Medicamentos medicamento) {
        try {
            citaMedicamentoDao.ingresaMedicamentoCita(cita.getIdCita(), medicamento.getCodigo_medicamento());
        } catch (Exception e) {
            
        }
    }

    @Override
    public void delete(Cita cita) {
        try {
            citaMedicamentoDao.eliminarMedicamentoCita(cita.getIdCita());
        } catch (Exception e) {
            
        }
    }

}
