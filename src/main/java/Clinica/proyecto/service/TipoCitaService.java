package Clinica.proyecto.service;

import Clinica.proyecto.domain.TipoCita;
import java.util.List;

public interface TipoCitaService {

    public List<TipoCita> getTipoCitas();

    public TipoCita getTipoCita(TipoCita tipoCita);


}
