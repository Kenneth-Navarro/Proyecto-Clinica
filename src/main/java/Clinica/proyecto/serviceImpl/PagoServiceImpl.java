package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.PagoDao;
import Clinica.proyecto.domain.Paciente;
import Clinica.proyecto.domain.Pago;
import Clinica.proyecto.service.PacienteService;
import Clinica.proyecto.service.PagoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoDao pagoDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PacienteService pacienteService;

    @Override
    @Transactional(readOnly = true)
    public List<Pago> getPagos() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerPagos");
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        // Execute the stored procedure
        storedProcedure.execute();

        // Get the results
        List<Object[]> resultados = storedProcedure.getResultList();
        List<Pago> pagosObtenidos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Pago pagoObtenido = new Pago();
            BigDecimal idPagoDecimal = (BigDecimal) resultado[0];
            Long idPago = idPagoDecimal.longValue();
            BigDecimal cedula = (BigDecimal) resultado[1];
            Long cedulaP = cedula.longValue();

            Paciente paciente2 = new Paciente();
            paciente2.setCedula(cedulaP);

            Paciente pacienteObtenida = pacienteService.getPaciente(paciente2);
            double monto = ((BigDecimal) resultado[2]).doubleValue();

            // Check the type of resultado[3] and convert it to LocalDate
            LocalDate fecha;
            if (resultado[3] instanceof java.sql.Timestamp) {
                fecha = ((java.sql.Timestamp) resultado[3]).toLocalDateTime().toLocalDate();
            } else if (resultado[3] instanceof LocalDate) {
                fecha = (LocalDate) resultado[3];
            } else {
                // Handle the case when the type is unexpected
                fecha = LocalDate.now(); // Set a default value or throw an exception
            }

            String metodoPago = (String) resultado[4];
            String estadoPago = (String) resultado[5];
            String detalle = (String) resultado[6];

            pagoObtenido.setIdPago(idPago);
            pagoObtenido.setPaciente(pacienteObtenida);
            pagoObtenido.setMonto(monto);
            pagoObtenido.setFecha(fecha);
            pagoObtenido.setMetodoPago(metodoPago);
            pagoObtenido.setEstadoPago(estadoPago);
            pagoObtenido.setDetalle(detalle);

            pagosObtenidos.add(pagoObtenido);
        }

        return pagosObtenidos;
    }

    @Override
    @Transactional(readOnly = true)
    public Pago getPago(Pago pago) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerPago");
        storedProcedure.registerStoredProcedureParameter("idPago", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("idPago", pago.getIdPago());

        storedProcedure.execute();
        
        List<Object[]> resultados = storedProcedure.getResultList();
        Pago pagoObtenido = new Pago();

        for (Object[] resultado : resultados) {
            
            BigDecimal idPagoDecimal = (BigDecimal) resultado[0];
            Long idPago = idPagoDecimal.longValue();
            BigDecimal cedula = (BigDecimal) resultado[1];
            Long cedulaP = cedula.longValue();

            Paciente paciente2 = new Paciente();
            paciente2.setCedula(cedulaP);

            Paciente pacienteObtenida = pacienteService.getPaciente(paciente2);
            double monto = ((BigDecimal) resultado[2]).doubleValue();

            // Check the type of resultado[3] and convert it to LocalDate
            LocalDate fecha;
            if (resultado[3] instanceof java.sql.Timestamp) {
                fecha = ((java.sql.Timestamp) resultado[3]).toLocalDateTime().toLocalDate();
            } else if (resultado[3] instanceof LocalDate) {
                fecha = (LocalDate) resultado[3];
            } else {
                // Handle the case when the type is unexpected
                fecha = LocalDate.now(); // Set a default value or throw an exception
            }

            String metodoPago = (String) resultado[4];
            String estadoPago = (String) resultado[5];
            String detalle = (String) resultado[6];

            pagoObtenido.setIdPago(idPago);
            pagoObtenido.setPaciente(pacienteObtenida);
            pagoObtenido.setMonto(monto);
            pagoObtenido.setFecha(fecha);
            pagoObtenido.setMetodoPago(metodoPago);
            pagoObtenido.setEstadoPago(estadoPago);
            pagoObtenido.setDetalle(detalle);

        }

        return pagoObtenido;
    }

    @Override
    @Transactional
    public void save(Pago pago, boolean accion, Long ID) {
        System.out.println(pago);
        if (accion) {
            try {
                pagoDao.ingresoPago(pago.getPaciente().getCedula(),
                        pago.getMonto(), pago.getFecha(), pago.getMetodoPago(),
                        pago.getEstadoPago(), pago.getDetalle());

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                pagoDao.actualizaPago(pago.getPaciente().getCedula(),
                        pago.getMonto(), pago.getFecha(), pago.getMetodoPago(),
                        pago.getEstadoPago(), pago.getDetalle(), ID);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Override
    @Transactional
    public void delete(Pago pago) {
        try{
            pagoDao.eliminarPago(pago.getIdPago());
        }catch(Exception e){
        }
    }

    @Override
    @Scheduled(fixedDelay = 90000)
    public void actualizaEstado() {
        try {
            pagoDao.actualizaEstadoPago();
        } catch (Exception e) {
        }
         
    }
    
    
}