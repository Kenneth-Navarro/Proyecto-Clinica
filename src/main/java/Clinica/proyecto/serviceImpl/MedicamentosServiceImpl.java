package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.MedicamentosDao;
import Clinica.proyecto.dao.MedicoDao;
import Clinica.proyecto.domain.Especialidad;
import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.service.MedicamentosService;
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

@Service
public class MedicamentosServiceImpl implements MedicamentosService {

    @Autowired
    private MedicamentosDao medicamentosDao;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true) //No altera la base de datos solo trae los datos
    public List<Medicamentos> getMedicamentos() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerMedicamentos");
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        /*Se ejecuta*/
        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        List<Medicamentos> medicamentosObtenidos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Medicamentos medicamentoObtenido = new Medicamentos();
            BigDecimal codigoDecimal = (BigDecimal) resultado[0];
            Long codigo = codigoDecimal.longValue();

            String nombre = (String) resultado[1];
            String descripcion = (String) resultado[2];
            String indicaciones = (String) resultado[3];
            String efectos_secundarios = (String) resultado[4];
           String fecha_caducidad = (String) resultado[5]; // Convertir a String
            double costo = ((BigDecimal) resultado[6]).doubleValue(); // Convertir a double
            String dosis = (String) resultado[7];

            medicamentoObtenido.setCodigo_medicamento(codigo);
            medicamentoObtenido.setNombre(nombre);
            medicamentoObtenido.setDescripcion(descripcion);
            medicamentoObtenido.setIndicaciones(indicaciones);
            medicamentoObtenido.setEfectos_secundarios(efectos_secundarios);
            medicamentoObtenido.setFecha_caducidad(fecha_caducidad);
            medicamentoObtenido.setCosto(costo);
            medicamentoObtenido.setDosis(dosis);

            medicamentosObtenidos.add(medicamentoObtenido);
        }

        return medicamentosObtenidos;
    }

    @Override
    @Transactional(readOnly = true) // solo lee la base de datos//
    public Medicamentos getMedicamentos(Medicamentos medicamentos) {

        /*Se realiza la configuración para iniciar con el procedimiento*/
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerMedicamento");
        storedProcedure.registerStoredProcedureParameter("codigo", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("codigo", medicamentos.getCodigo_medicamento());

        /*Se ejecuta*/
        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        Medicamentos medicamentoObtenido = new Medicamentos();

        for (Object[] resultado : resultados) {
            BigDecimal codigoDecimal = (BigDecimal) resultado[0];
            Long codigo = codigoDecimal.longValue();

            String nombre = (String) resultado[1];
            String descripcion = (String) resultado[2];
            String indicaciones = (String) resultado[3];
            String efectos_secundarios = (String) resultado[4];
            String fecha_caducidad = (String) resultado[5]; // Convertir a String
            double costo = ((BigDecimal) resultado[6]).doubleValue(); // Convertir a double
            String dosis = (String) resultado[7];

            medicamentoObtenido.setCodigo_medicamento(codigo);
            medicamentoObtenido.setNombre(nombre);
            medicamentoObtenido.setDescripcion(descripcion);
            medicamentoObtenido.setIndicaciones(indicaciones);
            medicamentoObtenido.setEfectos_secundarios(efectos_secundarios);
            medicamentoObtenido.setFecha_caducidad(fecha_caducidad);
            medicamentoObtenido.setCosto(costo);
            medicamentoObtenido.setDosis(dosis);
        }

        return medicamentoObtenido;
    }

//    @Override
//    @Transactional
//    public void save(Medicamentos medicamentos) {
//        medicamentosDao.save(medicamentos);
//    }
    @Override
    @Transactional
    public void delete(Medicamentos medicamentos) {
          try {
             Long codigo = medicamentos.getCodigo_medicamento();
        
              System.out.println("Ayuda2");
           medicamentosDao.eliminarMedicamento(codigo);
              System.out.println("Ayuda3");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    @Transactional
    public void save(Medicamentos medicamentos) {
        try {
           
            String nombre = medicamentos.getNombre();
            String descripcion = medicamentos.getDescripcion();
            String indicaciones = medicamentos.getIndicaciones();
            String efectos_secundarios = medicamentos.getEfectos_secundarios();
            String fecha_caducidad = medicamentos.getFecha_caducidad();
            double costo = medicamentos.getCosto();
            String dosis = medicamentos.getDosis();

            medicamentosDao.ingresoMedicamento(nombre, descripcion, indicaciones, efectos_secundarios, fecha_caducidad, costo, dosis);
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    @Override
    @Transactional
    public void update(Medicamentos medicamentos) {
        try {
           Long codigo = medicamentos.getCodigo_medicamento();
            String nombre = medicamentos.getNombre();
            String descripcion = medicamentos.getDescripcion();
            String indicaciones = medicamentos.getIndicaciones();
            String efectos_secundarios = medicamentos.getEfectos_secundarios();
            String fecha_caducidad = medicamentos.getFecha_caducidad();
            double costo = medicamentos.getCosto();
            String dosis = medicamentos.getDosis();
            System.out.println(medicamentos);
           medicamentosDao.actualizarMedicamento(codigo, nombre, descripcion, indicaciones, efectos_secundarios, fecha_caducidad, costo, dosis);
        } catch (Exception e) {
            System.out.println(e);
        }
    } 
    
    

}
