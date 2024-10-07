package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.TratamientoDao;
import Clinica.proyecto.domain.Especialidad;
import Clinica.proyecto.domain.Tratamiento;
import Clinica.proyecto.service.TratamientoService;
import java.time.LocalDate;
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
public class TratamientoServiceImpl implements TratamientoService {

    @Autowired
    private TratamientoDao tratamientoDao;
    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Tratamiento UltimoTratamiento() {
        try {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("UltimoTratamiento");
            storedProcedure.registerStoredProcedureParameter("Resul", void.class, ParameterMode.REF_CURSOR);

            /* Se ejecuta */
            storedProcedure.execute();

            /* Se trae los resultados */
            List<Object[]> resultados = storedProcedure.getResultList();
            Tratamiento ultimoTratamiento = new Tratamiento();

            for (Object[] resultado : resultados) {
                BigDecimal IDdecimal = (BigDecimal) resultado[0];
                Long ID_Tratamiento = IDdecimal.longValue();
                String nombre = (String) resultado[1];
                String duracion = (String) resultado[2];
                double costo = ((BigDecimal) resultado[3]).doubleValue();

                ultimoTratamiento.setID_Tratamiento(ID_Tratamiento);
                ultimoTratamiento.setNombre(nombre);
                ultimoTratamiento.setDuracion(duracion);
                ultimoTratamiento.setCosto(costo);
            }

            return ultimoTratamiento;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)

    public List<Tratamiento> getTratamientos() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerTratamientos");
        storedProcedure.registerStoredProcedureParameter("resultado", void.class, ParameterMode.REF_CURSOR);

        /*Se ejecuta*/
        storedProcedure.execute();

        /*Se trae los resultados*/
        List<Object[]> resultados = storedProcedure.getResultList();
        List<Tratamiento> TratamientosObtenidos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Tratamiento tratamientoObtenido = new Tratamiento();
            BigDecimal IdTratamiento = (BigDecimal) resultado[0];
            Long ID_Tratamiento = IdTratamiento.longValue();
            String nombre = (String) resultado[1];
            String duracion = (String) resultado[2];
            double costo = ((BigDecimal) resultado[3]).doubleValue();
            
            tratamientoObtenido.setID_Tratamiento(ID_Tratamiento);
            tratamientoObtenido.setNombre(nombre);
            tratamientoObtenido.setDuracion(duracion);
            tratamientoObtenido.setCosto(costo);

            TratamientosObtenidos .add( tratamientoObtenido);

        }

        return  TratamientosObtenidos;
    }

    @Override
    @Transactional(readOnly = true)
    public Tratamiento getTratamiento(Tratamiento tratamiento) {

        /*Se realiza la configuración para iniciar con el procedimiento*/
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerTratamiento");
        storedProcedure.registerStoredProcedureParameter("id_trat", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("Resultado", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("id_trat", tratamiento.getID_Tratamiento());

        /*Se ejecuta*/
        storedProcedure.execute();

        /*Se trae los resultados*/
        List<Object[]> resultados = storedProcedure.getResultList();
        Tratamiento tratamientoObtenido = new Tratamiento();

        for (Object[] resultado : resultados) {
            BigDecimal IdTratamiento = (BigDecimal) resultado[0];
            Long ID_Tratamiento = IdTratamiento.longValue();
            String nombre = (String) resultado[1];
            String duracion = (String) resultado[2];
            double costo = ((BigDecimal) resultado[3]).doubleValue();

            tratamientoObtenido.setID_Tratamiento(ID_Tratamiento);
            tratamientoObtenido.setNombre(nombre);
            tratamientoObtenido.setDuracion(duracion);
            tratamientoObtenido.setCosto(costo);
        }
        return tratamientoObtenido;

    }

    @Override
    @Transactional
    public void save(Tratamiento tratamiento, boolean accion, Long id_tratamiento) {
        System.out.println(accion);
        if (accion) {
            try {
                String Nombre = tratamiento.getNombre();
                String Duracion = tratamiento.getDuracion();
                double Costo = tratamiento.getCosto();
                tratamientoDao.ingresoTratamiento(Nombre, Duracion, Costo);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                String Nombre = tratamiento.getNombre();
                String Duracion = tratamiento.getDuracion();
                double Costo = tratamiento.getCosto();
                long id = tratamiento.getID_Tratamiento();
                tratamientoDao.actualizarTratamiento(Nombre, Duracion, Costo, id);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Override
    @Transactional
    public void delete(Tratamiento tratamiento) {
        try {
            tratamientoDao.eliminarTratamiento(tratamiento.getID_Tratamiento());
        } catch (Exception e) {
        }
    }

}
