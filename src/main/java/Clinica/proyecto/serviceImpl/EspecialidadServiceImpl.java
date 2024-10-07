package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.EspecialidadDao;
import Clinica.proyecto.domain.Especialidad;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.service.EspecialidadService;
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
public class EspecialidadServiceImpl implements EspecialidadService{

     @Autowired
    private EspecialidadDao especialidadDao;
     
     @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true) //No altera la base de datos solo trae los datos
    public List<Especialidad> getEspecialidades() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneEspecialidades");
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        
        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        List<Especialidad> especialidades = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Especialidad especialidadObtenida = new Especialidad();
            BigDecimal idEspecialidad = (BigDecimal) resultado[0];
            Long id = idEspecialidad.longValue();
            
            String nombre = (String) resultado[1];
            
            especialidadObtenida.setIdEspecialidad(id);
            especialidadObtenida.setNombre(nombre);
            
            
            
            
            especialidades.add(especialidadObtenida);
        }
        
        return especialidades;
    }

    @Override
    @Transactional(readOnly = true) // solo lee la base de datos//
    public List<Especialidad> getEspecialidad(Long ID) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneEspecialidad");
        storedProcedure.registerStoredProcedureParameter("idEs", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("idEs", ID);

        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        List<Especialidad> especialidades = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Especialidad especialidadObtenida = new Especialidad();
            BigDecimal idEspecialidad = (BigDecimal) resultado[0];
            Long id = idEspecialidad.longValue();
            
            String nombre = (String) resultado[1];
            
            especialidadObtenida.setIdEspecialidad(id);
            especialidadObtenida.setNombre(nombre);
            
            
            
            
            especialidades.add(especialidadObtenida);
        }
        
        return especialidades;
    }

    @Override
    @Transactional
    public void save(Especialidad especialidad) {
        especialidadDao.save(especialidad);
    }

    @Override
    @Transactional
    public void delete(Especialidad especialidad) {
        especialidadDao.delete(especialidad);
    }


}
