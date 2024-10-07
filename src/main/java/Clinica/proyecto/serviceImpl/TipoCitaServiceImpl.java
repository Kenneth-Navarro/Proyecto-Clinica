package Clinica.proyecto.serviceImpl;

import Clinica.proyecto.dao.TipoCitaDao;
import Clinica.proyecto.domain.TipoCita;
import Clinica.proyecto.service.TipoCitaService;
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
public class TipoCitaServiceImpl implements TipoCitaService{

     @Autowired
    private TipoCitaDao tipoTipoCitaDao;
     
      @Autowired
    private EntityManager entityManager;
     

    @Override
    @Transactional(readOnly = true) //No altera la base de datos solo trae los datos
    public List<TipoCita> getTipoCitas() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneTipoCitas");
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        storedProcedure.execute();
        
        List<Object[]> resultados = storedProcedure.getResultList();
        List<TipoCita> tiposCObtenidos = new ArrayList<>();
        
        
        for (Object[] resultado : resultados) {
            TipoCita tipo = new TipoCita();
            
             BigDecimal IDTipo = (BigDecimal) resultado[0];
             Long idTipo = IDTipo.longValue();
             String nombre = (String) resultado[1];
             
             tipo.setIdTipoCita(idTipo);
             tipo.setNombre(nombre);
             
             tiposCObtenidos.add(tipo);
            
        }
        
        return tiposCObtenidos;
    }

    @Override
    @Transactional(readOnly = true) // solo lee la base de datos//
    public TipoCita getTipoCita(TipoCita tipoCita) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("obtieneTipoCita");
        storedProcedure.registerStoredProcedureParameter("id", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter("id", tipoCita.getIdTipoCita());
        

        storedProcedure.execute();
        
        List<Object[]> resultados = storedProcedure.getResultList();
        TipoCita tipoCitaObtenida = new TipoCita();
        
        for (Object[] resultado : resultados) {
            
             BigDecimal IDTipo = (BigDecimal) resultado[0];
             Long idTipo = IDTipo.longValue();
             String nombre = (String) resultado[1];
             
             tipoCitaObtenida.setIdTipoCita(idTipo);
             tipoCitaObtenida.setNombre(nombre);
             
            
        }
        

        return tipoCitaObtenida;
    }


}
