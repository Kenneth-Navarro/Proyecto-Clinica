package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.Cita_MedicamentoDao;
import Clinica.proyecto.dao.Cita_TratamientoDao;
import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Tratamiento;
import Clinica.proyecto.service.Cita_TratamientoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Cita_TratamientoServiceImpl implements Cita_TratamientoService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Cita_TratamientoDao citaTratamientoDao;

    @Autowired
    private TratamientoServiceImpl tratamientoServiceImpl;

    @Override
    public List<Tratamiento> getCitaTratamientos(Long idCita) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneTratamientoCita");
        storedProcedure.registerStoredProcedureParameter("idCita", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        storedProcedure.setParameter("idCita", idCita);

        storedProcedure.execute();

        List<Object> resultados = storedProcedure.getResultList();
        List<Tratamiento> devueltos = new ArrayList<>();

        for (Object resultado : resultados) {
            BigDecimal IDTratamiento = (BigDecimal) resultado;
            Long idTratamiento = IDTratamiento.longValue();

            Tratamiento devuelto = new Tratamiento();
            devuelto.setID_Tratamiento(idTratamiento);

            devuelto = tratamientoServiceImpl.getTratamiento(devuelto);
            
            devueltos.add(devuelto);
        }

        return devueltos;

    }

    @Override
    public void save(Cita cita, Tratamiento tratamiento) {
        try {
            citaTratamientoDao.ingresaCitaTratamiento(cita.getIdCita(), tratamiento.getID_Tratamiento());
        } catch (Exception e) {
        }
    }

    @Override
    public void delete(Cita cita) {
        try {
            citaTratamientoDao.eliminarTratamientooCita(cita.getIdCita());
        } catch (Exception e) {
        }
    }

}
