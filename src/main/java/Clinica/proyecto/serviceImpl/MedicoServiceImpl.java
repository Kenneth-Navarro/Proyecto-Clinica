package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.MedicoDao;
import Clinica.proyecto.domain.Especialidad;
import Clinica.proyecto.domain.Medico;
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
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoDao medicoDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EspecialidadServiceImpl especialidadService;

    @Override
    @Transactional(readOnly = true) //No altera la base de datos solo trae los datos
    public List<Medico> getMedicos() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneMedicos");
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        /*Se ejecuta*/
        storedProcedure.execute();

        /*Se trae los resultados*/
        List<Object[]> resultados = storedProcedure.getResultList();
        List<Medico> medicosObtenidos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Medico medicoObtenido = new Medico();
            BigDecimal cedulaDecimal = (BigDecimal) resultado[0];
            Long cedula = cedulaDecimal.longValue();
            String nombre = (String) resultado[1];
            String primerApellido = (String) resultado[2];
            String segApellidp = (String) resultado[3];
            String telefono = (String) resultado[4];

            medicoObtenido.setCedula(cedula);
            medicoObtenido.setNombre(nombre);
            medicoObtenido.setPrimerApellido(primerApellido);
            medicoObtenido.setSegundoApellido(segApellidp);
            medicoObtenido.setTelefono(telefono);

            medicosObtenidos.add(medicoObtenido);

        }

        return medicosObtenidos;
    }

    @Override
    @Transactional(readOnly = true) // solo lee la base de datos//
    public Medico getMedico(Medico medico) {

        /*Se realiza la configuración para iniciar con el procedimiento*/
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneMedico");
        storedProcedure.registerStoredProcedureParameter("IDCed", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("IDCed", medico.getCedula());

        /*Se ejecuta*/
        storedProcedure.execute();

        /*Se trae los resultados*/
        List<Object[]> resultados = storedProcedure.getResultList();
        Medico medicoObtenido = new Medico();

        for (Object[] resultado : resultados) {
            BigDecimal cedulaDecimal = (BigDecimal) resultado[0];
            Long cedula = cedulaDecimal.longValue();
            BigDecimal idEspecialidad = (BigDecimal) resultado[1];
            Long idEsp = idEspecialidad.longValue();
            int numColegiado = ((BigDecimal) resultado[2]).intValue();

            Especialidad especialidadObtenida = null;
            for (Especialidad especialidad : especialidadService.getEspecialidad(idEsp)) {
                especialidadObtenida = especialidad;
            }

            String nombre = (String) resultado[3];
            String primerApellido = (String) resultado[4];
            String segApellidp = (String) resultado[5];
            String correo = (String) resultado[6];
            String telefono = (String) resultado[7];
            String provincia = (String) resultado[8];
            String canton = (String) resultado[9];
            String distrito = (String) resultado[10];
            String otros = (String) resultado[11];
            int edad = ((BigDecimal) resultado[12]).intValue();
            
            int gene = (Integer)resultado[13];
            boolean genero;
            if (gene == 0) {
                genero = false;
            }else{
                genero = true;
            }
            
            
            medicoObtenido.setCedula(cedula);
            medicoObtenido.setEspecialidad(especialidadObtenida);
            medicoObtenido.setNumColegiado(numColegiado);
            medicoObtenido.setNombre(nombre);
            medicoObtenido.setPrimerApellido(primerApellido);
            medicoObtenido.setSegundoApellido(segApellidp);
            medicoObtenido.setCorreo(correo);
            medicoObtenido.setTelefono(telefono);
            medicoObtenido.setProvincia(provincia);
            medicoObtenido.setCanton(canton);
            medicoObtenido.setDistrito(distrito);
            medicoObtenido.setOtros(otros);
            medicoObtenido.setEdad(edad);
            medicoObtenido.setGenero(genero);

        }

        return medicoObtenido;

    }

    @Override
    @Transactional
    public void save(Medico medico, boolean accion, Long ced) {
        if (accion) {
            try {
                medicoDao.ingresoMedico(medico.getCedula(), medico.getEspecialidad().getIdEspecialidad(),
                        medico.getNumColegiado(), medico.getNombre(), medico.getPrimerApellido(),
                        medico.getSegundoApellido(), medico.getCorreo(), medico.getTelefono(), medico.getProvincia(),
                        medico.getCanton(), medico.getDistrito(), medico.getOtros(), medico.getEdad(), medico.isGenero());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else{
           try {
                medicoDao.actualizaMedico(medico.getCedula(), medico.getEspecialidad().getIdEspecialidad(),
                        medico.getNumColegiado(), medico.getNombre(), medico.getPrimerApellido(),
                        medico.getSegundoApellido(), medico.getCorreo(), medico.getTelefono(), medico.getProvincia(),
                        medico.getCanton(), medico.getDistrito(), medico.getOtros(), medico.getEdad(), medico.isGenero(), ced);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Override
    @Transactional
    public void delete(Medico medico) {
        try{
            medicoDao.eliminarMedico(medico.getCedula());
        }catch(Exception e){
        }
        
    }

}
