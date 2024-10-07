package Clinica.proyecto.controller;

import Clinica.proyecto.domain.Pago;
import Clinica.proyecto.domain.Pago;
import Clinica.proyecto.domain.Pago;
import Clinica.proyecto.service.CitaService;
import Clinica.proyecto.service.PacienteService;
import Clinica.proyecto.service.PagoService;
import Clinica.proyecto.service.PagoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping("/pago")
public class PagoController {

    @Autowired //Sirve para crear autoinstancias
    private PagoService pagoService;
    
     @Autowired //Sirve para crear autoinstancias
    private CitaService citaService;

    @Autowired //Sirve para crear autoinstancias
    private PacienteService pacienteService;

    @GetMapping("/crudPago")
    public String inicio(Model model) {
        var pagos = pagoService.getPagos();
        var pacientes = pacienteService.getPacientes();
        model.addAttribute("pagos", pagos);
        model.addAttribute("pacientes", pacientes);
        return "/pago/crudPago";
    }
    
    @GetMapping("/modificar/{idPago}")
    public String pagoModificar(Pago pago, Model model) {
        var pacientes = pacienteService.getPacientes();
        model.addAttribute("pacientes", pacientes);
        var pagoObtenido = pagoService.getPago(pago);
        model.addAttribute("pago", pagoObtenido);
        model.addAttribute("identificador", pago.getIdPago());
        return "/pago/modifica";
    }

    @GetMapping("/ver/{idPago}")
    public String pagoVer(Pago pago, Model model) {
        pago = pagoService.getPago(pago);
        model.addAttribute("pago", pago);
        return "/pago/ver";
    }

     @PostMapping("/actualizarPago")
    public String pagoActualizar(Pago pago, Long identificador) {
        pagoService.save(pago, false, identificador);
        return "redirect:/pago/crudPago";
    }
    
    @PostMapping("/guardarPago")
    public String pagoGuardar(Pago pago, Long idCita) {
        
        pago.setEstadoPago("Procesando");
        pagoService.save(pago, true, null);
        citaService.actualizaEstado(idCita);
        
        return "redirect:/cita/crudCita";
    }

    @GetMapping("/eliminar/{idPago}")
    public String pagoEliminar(Pago pago) {
        pagoService.delete(pago);
        return "redirect:/pago/crudPago";
    }

}