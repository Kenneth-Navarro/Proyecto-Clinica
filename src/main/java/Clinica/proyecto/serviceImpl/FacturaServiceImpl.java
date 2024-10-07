package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.FacturaDao;
import Clinica.proyecto.domain.Factura;
import Clinica.proyecto.domain.Pago;
import Clinica.proyecto.service.FacturaService;
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
public class FacturaServiceImpl implements FacturaService{

     @Autowired
    private FacturaDao facturaDao;
     
     @Autowired
    private EntityManager entityManager;

    @Autowired
    private PagoServiceImpl pagoService;

    @Override
    @Transactional(readOnly = true) //No altera la base de datos solo trae los datos
    public List<Factura> getFacturas() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerFacturas");
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        
        storedProcedure.execute();
        
        List<Object[]> resultados = storedProcedure.getResultList();
        List<Factura> facturasObtenidas = new ArrayList<>();
        
        for (Object[] resultado : resultados) {
            Factura facturaObtenida = new Factura();
            BigDecimal idFacturaDecimal = (BigDecimal) resultado[0];
            Long idFactura = idFacturaDecimal.longValue();
            BigDecimal idPago = (BigDecimal) resultado[1];
            Long idpagos = idPago.longValue();
            
            Pago pago2 = new Pago();
            pago2.setIdPago(idpagos);
            
            Pago pagoObtenido = pagoService.getPago(pago2);
            
            facturaObtenida.setIdFactura(idFactura);
            facturaObtenida.setPago(pagoObtenido);
            
            facturasObtenidas.add(facturaObtenida);
            
        }
        
        
        return facturasObtenidas;
        
    }

    @Override
    @Transactional(readOnly = true) // solo lee la base de datos//
    public Factura getFactura(Factura factura) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtenerFactura");
        storedProcedure.registerStoredProcedureParameter("idFactura", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("idFactura", factura.getIdFactura());
        
         storedProcedure.execute();
         
         List<Object[]> resultados = storedProcedure.getResultList();
         Factura facturaObtenida = new Factura();
        
        for (Object[] resultado : resultados) {
            
            BigDecimal idFacturaDecimal = (BigDecimal) resultado[0];
            Long idFactura = idFacturaDecimal.longValue();
            BigDecimal idPago = (BigDecimal) resultado[1];
            Long idpagos = idPago.longValue();
            
            Pago pago2 = new Pago();
            pago2.setIdPago(idpagos);
            
            Pago pagoObtenido = pagoService.getPago(pago2);
            
            facturaObtenida.setIdFactura(idFactura);
            facturaObtenida.setPago(pagoObtenido);
            
            
            
        }
        
        
        return facturaObtenida;
        
        
    }

    @Override
    @Transactional
    public void save(Factura factura, boolean accion, Long ID) {
       if (accion) {
            try {
                facturaDao.ingresoFactura(factura.getPago().getIdPago());

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                facturaDao.actualizaFactura(factura.getPago().getIdPago(),ID);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Factura factura) {
        facturaDao.delete(factura);
    }


}