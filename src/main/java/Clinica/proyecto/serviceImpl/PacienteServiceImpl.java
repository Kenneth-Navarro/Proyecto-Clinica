package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.PacienteDao;
import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Especialidad;
import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.domain.Paciente;
import Clinica.proyecto.domain.Tratamiento;
import Clinica.proyecto.service.Cita_MedicamentoService;
import Clinica.proyecto.service.Cita_TratamientoService;
import Clinica.proyecto.service.MedicoService;
import Clinica.proyecto.service.PacienteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteDao pacienteDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired //Sirve para crear autoinstancias
    private MedicoService medicoService;

    @Autowired //Sirve para crear autoinstancias
    private Cita_TratamientoService citaTratamientoService;
    
    @Autowired //Sirve para crear autoinstancias
    private Cita_MedicamentoService citaMedicamentoService;

    @Override
    @Transactional(readOnly = true) //No altera la base de datos solo trae los datos
    public List<Paciente> getPacientes() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtienePacientes");
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        /*Se ejecuta*/
        storedProcedure.execute();

        /*Se trae los resultados*/
        List<Object[]> resultados = storedProcedure.getResultList();
        List<Paciente> pacientesObtenidos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Paciente pacienteObtenido = new Paciente();
            BigDecimal cedulaDecimal = (BigDecimal) resultado[0];
            Long cedula = cedulaDecimal.longValue();
            String nombre = (String) resultado[1];
            String primerApellido = (String) resultado[2];
            String telefono = (String) resultado[3];

            pacienteObtenido.setCedula(cedula);
            pacienteObtenido.setNombre(nombre);
            pacienteObtenido.setPrimerApellido(primerApellido);
            pacienteObtenido.setTelefono(telefono);

            pacientesObtenidos.add(pacienteObtenido);
        }

        return pacientesObtenidos;
    }

    @Override
    public Paciente getPaciente(Paciente paciente) {

        /*Se realiza la configuración para iniciar con el procedimiento*/
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtienePaciente");
        storedProcedure.registerStoredProcedureParameter("IDCed", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("IDCed", paciente.getCedula());

        /*Se ejecuta*/
        storedProcedure.execute();

        /*Se trae los resultados*/
        List<Object[]> resultados = storedProcedure.getResultList();
        Paciente pacienteObtenido = new Paciente();

        for (Object[] resultado : resultados) {
            BigDecimal cedulaDecimal = (BigDecimal) resultado[0];
            Long cedula = cedulaDecimal.longValue();
            String nombre = (String) resultado[1];
            String primerApellido = (String) resultado[2];
            String segApellidp = (String) resultado[3];
            String correo = (String) resultado[4];
            String telefono = (String) resultado[5];
            String provincia = (String) resultado[6];
            String canton = (String) resultado[7];
            String distrito = (String) resultado[8];
            String otros = (String) resultado[9];
            int edad = ((BigDecimal) resultado[10]).intValue();
            int gene = (Integer) resultado[11];
            boolean genero;
            if (gene == 0) {
                genero = false;
            } else {
                genero = true;
            }

            pacienteObtenido.setCedula(cedula);
            pacienteObtenido.setNombre(nombre);
            pacienteObtenido.setPrimerApellido(primerApellido);
            pacienteObtenido.setSegundoApellido(segApellidp);
            pacienteObtenido.setCorreo(correo);
            pacienteObtenido.setTelefono(telefono);
            pacienteObtenido.setProvincia(provincia);
            pacienteObtenido.setCanton(canton);
            pacienteObtenido.setDistrito(distrito);
            pacienteObtenido.setOtros(otros);
            pacienteObtenido.setEdad(edad);
            pacienteObtenido.setGenero(genero);

        }

        return pacienteObtenido;
    }

    @Override
    @Transactional
    public void save(Paciente paciente, boolean accion, Long ced
    ) {
        if (accion) {
            try {

                pacienteDao.ingresarPaciente(paciente.getCedula(), paciente.getNombre(), paciente.getPrimerApellido(), paciente.getSegundoApellido(),
                        paciente.getTelefono(), paciente.getCorreo(), paciente.getProvincia(), paciente.getDistrito(),
                        paciente.getCanton(), paciente.getOtros(), paciente.getEdad(), paciente.isGenero());

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                pacienteDao.actualizarPaciente(paciente.getCedula(), paciente.getNombre(), paciente.getPrimerApellido(),
                        paciente.getSegundoApellido(), paciente.getTelefono(), paciente.getCorreo(), paciente.getProvincia(),
                        paciente.getDistrito(), paciente.getCanton(), paciente.getOtros(), paciente.getEdad(), paciente.isGenero(), ced);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Override
    @Transactional
    public void delete(Paciente paciente
    ) {
        try {
            pacienteDao.eliminarPaciente(paciente.getCedula());
        } catch (Exception e) {
        }

    }

    @Override
    @Transactional
    public List<Cita> getCitas(Paciente paciente) {
        /*Se realiza la configuración para iniciar con el procedimiento*/
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("vista_paciente_citas");
        storedProcedure.registerStoredProcedureParameter("ID", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("RESULTADO", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("ID", paciente.getCedula());

        /*Se ejecuta*/
        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        List<Cita> citas = new ArrayList<>();

        for (Object[] resultado : resultados) {

            Cita cita = new Cita();

            BigDecimal idCita = (BigDecimal) resultado[0];
            Long id = idCita.longValue();

            Timestamp fech = (Timestamp) resultado[1];
            Instant instant = fech.toInstant();

            LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            String motivo_cita = (String) resultado[2];

            BigDecimal cedulaMedico = (BigDecimal) resultado[3];
            BigDecimal cedulaPaciente = (BigDecimal) resultado[4];

            Long medico = cedulaMedico.longValue();
            Long paciente1 = cedulaPaciente.longValue();

            Medico med = new Medico();
            med.setCedula(medico);

            med = medicoService.getMedico(med);

            Paciente pac = new Paciente();
            pac.setCedula(paciente1);

            cita.setIdCita(id);
            cita.setFecha(fecha);
            cita.setMotivoCita(motivo_cita);
            cita.setMedico(med);
            cita.setPaciente(pac);

            citas.add(cita);

        }

        return citas;
    }

    @Override
    @Transactional
    public Tratamiento getTratamiento(Tratamiento tratamiento) {
        
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("vista_paciente_tratamiento");
        storedProcedure.registerStoredProcedureParameter("ID", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("RESULTADO", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("ID", tratamiento.getID_Tratamiento());

        /*Se ejecuta*/
        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        Tratamiento tratamientoObtenido = new Tratamiento();

        for (Object[] resultado : resultados) {
            String nombre = (String) resultado[1];
            String duracion = (String) resultado[2];

            tratamientoObtenido.setNombre(nombre);
            tratamientoObtenido.setDuracion(duracion);
        }

        return tratamientoObtenido;
    }

    @Override
    @Transactional
    public List<Tratamiento> getTratamientos(List<Cita> citas) {
        List<Tratamiento> tratamientos = new ArrayList<>();

        for (Cita cita : citas) {
            
            List<Tratamiento> tratamientosObtenidos = citaTratamientoService.getCitaTratamientos(cita.getIdCita());
            
            for (Tratamiento tratamientosObtenido : tratamientosObtenidos) {
                Tratamiento trObtenido = getTratamiento(tratamientosObtenido);
                tratamientos.add(trObtenido);
            }
        }

        return tratamientos;
    }

    @Override
    public Medicamentos getMedicamento(Medicamentos medicamento) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("vista_paciente_medicamentos");
        storedProcedure.registerStoredProcedureParameter("ID", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("RESULTADO", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("ID", medicamento.getCodigo_medicamento());
        
        /*Se ejecuta*/
        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        Medicamentos medicamentoObtenido = new Medicamentos();

        for (Object[] resultado : resultados) {
            String nombre = (String) resultado[0];
            BigDecimal idMedicamento = (BigDecimal) resultado[1];
            Long id = idMedicamento.longValue();
            
            String descripción = (String) resultado[2];
            medicamentoObtenido.setNombre(nombre);
            medicamentoObtenido.setDescripcion(descripción);
        }

        return medicamentoObtenido;
    }

    @Override
    public List<Medicamentos> getMedicamentos(List<Cita> citas) {
        List<Medicamentos> medicamentos = new ArrayList<>();

        for (Cita cita : citas) {
            
            List<Medicamentos> medicamentosObtenidos = citaMedicamentoService.getCitasMedicamentos(cita.getIdCita());
            
            for (Medicamentos medicamentosObtenido : medicamentosObtenidos) {
                Medicamentos medObtenido = getMedicamento(medicamentosObtenido);
                medicamentos.add(medObtenido);
            }
        }

        return medicamentos;
    }

}
