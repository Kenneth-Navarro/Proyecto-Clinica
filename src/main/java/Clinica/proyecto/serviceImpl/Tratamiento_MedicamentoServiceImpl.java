package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.Tratamiento_MedicamentoDao;
import Clinica.proyecto.domain.Especialidad;
import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.domain.Tratamiento;

import Clinica.proyecto.domain.Tratamiento_Medicamento;
import Clinica.proyecto.service.Tratamiento_MedicamentoService;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@EnableTransactionManagement
@Service
public class Tratamiento_MedicamentoServiceImpl implements Tratamiento_MedicamentoService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Tratamiento_MedicamentoDao tratamiento_medicamentoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Tratamiento_Medicamento> getTratamiento_Medicamentos(Tratamiento tratamiento) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerTratamientoMedicamento");
        storedProcedure.registerStoredProcedureParameter("id_tratamiento", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("Resultado", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("id_tratamiento", tratamiento.getID_Tratamiento());

        /* Se ejecuta */
        storedProcedure.execute();

        /* Se trae los resultados */
        List<Object[]> resultados = storedProcedure.getResultList();
        List<Tratamiento_Medicamento> tratamiento_medicamentosObtenidos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            long idtm = ((BigDecimal) resultado[0]).longValue();
            long ID_Tratamiento = ((BigDecimal) resultado[1]).intValue();
            long ID_Medicamento = ((BigDecimal) resultado[2]).intValue();

            Tratamiento_Medicamento tratamientoMedicamento = new Tratamiento_Medicamento();
            tratamientoMedicamento.setIdTM(idtm);
            tratamientoMedicamento.setIdMedicamento(ID_Medicamento);
            tratamientoMedicamento.setIdTratamiento(ID_Tratamiento);

            tratamiento_medicamentosObtenidos.add(tratamientoMedicamento);
        }

        return tratamiento_medicamentosObtenidos;
    }

    @Override
    @Transactional(readOnly = true)
    public Tratamiento_Medicamento getTratamiento_Medicamento(Tratamiento_Medicamento tratamiento_medicamento) {
        /*Se realiza la configuración para iniciar con el procedimiento*/
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerTratamiento");
        storedProcedure.registerStoredProcedureParameter("id_trat", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("Resultado", void.class, ParameterMode.REF_CURSOR);


        /*Se ejecuta*/
        storedProcedure.execute();

        /*Se trae los resultados*/
        List<Object[]> resultados = storedProcedure.getResultList();
        Tratamiento tratamiento_MedicamentoObtenido = new Tratamiento();

        for (Object[] resultado : resultados) {
            BigDecimal IdTratamiento = (BigDecimal) resultado[0];
            Long ID_Tratamiento = IdTratamiento.longValue();
            String nombre = (String) resultado[1];
            String duracion = (String) resultado[2];
            double costo = ((BigDecimal) resultado[3]).doubleValue();

            tratamiento_MedicamentoObtenido.setID_Tratamiento(ID_Tratamiento);
            tratamiento_MedicamentoObtenido.setNombre(nombre);
            tratamiento_MedicamentoObtenido.setDuracion(duracion);
            tratamiento_MedicamentoObtenido.setCosto(costo);
        }
        return tratamiento_medicamento;

    }

    @Override
    @Transactional
    public void save(long ID_Tratamiento, long ID_Medicamento) {

        try {
            tratamiento_medicamentoDao.ingresarTratamiento_medicamento(ID_Tratamiento, ID_Medicamento);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    @Transactional
    public void delete(long ID_tratamiento) {
        try {
            tratamiento_medicamentoDao.eliminarTratamiento_Medicamento(ID_tratamiento);
        } catch (Exception e) {
            System.out.println(e);

        }
    }


}
