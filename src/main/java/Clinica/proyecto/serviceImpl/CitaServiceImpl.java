package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.CitaDao;
import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.domain.Paciente;
import Clinica.proyecto.domain.TipoCita;
import Clinica.proyecto.service.CitaService;
import Clinica.proyecto.service.MedicoService;
import Clinica.proyecto.service.PacienteService;
import Clinica.proyecto.service.TipoCitaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
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
public class CitaServiceImpl implements CitaService {

    @Autowired //Sirve para crear autoinstancias
    private MedicoService medicoService;

    @Autowired //Sirve para crear autoinstancias
    private PacienteService pacienteService;

    @Autowired //Sirve para crear autoinstancias
    private TipoCitaService tipoCitaService;

    @Autowired
    private CitaDao citaDao;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true) //No altera la base de datos solo trae los datos
    public List<Cita> getCitas() {

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneCitas");
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        List<Cita> citasObtenidas = new ArrayList<>();
        Medico medico = new Medico();
        Paciente paciente = new Paciente();

        for (Object[] resultado : resultados) {
            Cita cita = new Cita();

            BigDecimal IDCita = (BigDecimal) resultado[0];
            Long idcita = IDCita.longValue();
            BigDecimal CEDMedico = (BigDecimal) resultado[1];
            Long cedMedico = CEDMedico.longValue();
            BigDecimal CEDPaciente = (BigDecimal) resultado[2];
            Long cedPaciente = CEDPaciente.longValue();
            /*Se obtiene y se parcea la fecha a localdate*/
            Timestamp fech = (Timestamp) resultado[3];
            Instant instant = fech.toInstant();
            LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            String estado = (String) resultado[4];

            paciente.setCedula(cedPaciente);
            medico.setCedula(cedMedico);

            cita.setIdCita(idcita);
            cita.setPaciente(pacienteService.getPaciente(paciente));
            cita.setMedico(medicoService.getMedico(medico));
            cita.setFecha(fecha);
            cita.setEstado(estado);

            citasObtenidas.add(cita);

        }

        return citasObtenidas;
    }

    @Override
    @Transactional(readOnly = true) // solo lee la base de datos//
    public Cita getCita(Cita cita) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneCita");
        storedProcedure.registerStoredProcedureParameter("id", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("id", cita.getIdCita());

        storedProcedure.execute();

        List<Object[]> resultados = storedProcedure.getResultList();
        Cita citaObtenida = new Cita();
        Medico medico = new Medico();
        Paciente paciente = new Paciente();
        TipoCita tipo = new TipoCita();

        for (Object[] resultado : resultados) {

            BigDecimal IDCita = (BigDecimal) resultado[0];
            Long idcita = IDCita.longValue();
            BigDecimal CEDMedico = (BigDecimal) resultado[1];
            Long cedMedico = CEDMedico.longValue();
            BigDecimal CEDPaciente = (BigDecimal) resultado[2];
            Long cedPaciente = CEDPaciente.longValue();
            /*Se obtiene y se parcea la fecha a localdate*/
            Timestamp fech = (Timestamp) resultado[3];
            Instant instant = fech.toInstant();
            LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            String estado = (String) resultado[4];
            String motivo = (String) resultado[5];
            BigDecimal IDTIPOCITA = (BigDecimal) resultado[6];
            Long idTipoCita = IDTIPOCITA.longValue();

            paciente.setCedula(cedPaciente);
            medico.setCedula(cedMedico);
            tipo.setIdTipoCita(idTipoCita);
            tipo = tipoCitaService.getTipoCita(tipo);

            citaObtenida.setIdCita(idcita);
            citaObtenida.setPaciente(pacienteService.getPaciente(paciente));
            citaObtenida.setMedico(medicoService.getMedico(medico));
            citaObtenida.setFecha(fecha);
            citaObtenida.setEstado(estado);
            citaObtenida.setMotivoCita(motivo);
            citaObtenida.setTipo(tipo);

        }

        return citaObtenida;
    }

    @Override
    @Transactional
    public void save(Cita cita, boolean accion) {
        try {
            
            if (accion) {
                citaDao.ingresaCita(cita.getMedico().getCedula(), cita.getPaciente().getCedula(), cita.getTipo().getIdTipoCita(),
                        cita.getFecha(), cita.getMotivoCita(), cita.getEstado());
            }else{
                citaDao.actualizaCita(cita.getMedico().getCedula(), cita.getPaciente().getCedula(), 
                        cita.getTipo().getIdTipoCita(), cita.getFecha(), cita.getMotivoCita(), cita.getEstado(), cita.getIdCita());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    @Transactional
    public void delete(Cita cita) {
        citaDao.delete(cita);
    }

    @Override
    public void actualizaEstado(Long cita) {
        try {
            citaDao.actualizaEstadoCita(cita);
        } catch (Exception e) {
        }
    }

}
