package Clinica.proyecto.controller;

import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.domain.TipoCita;
import Clinica.proyecto.domain.Tratamiento;
import Clinica.proyecto.service.CitaService;
import Clinica.proyecto.service.Cita_MedicamentoService;
import Clinica.proyecto.service.Cita_TratamientoService;
import Clinica.proyecto.service.MedicamentosService;
import Clinica.proyecto.service.MedicoService;
import Clinica.proyecto.service.PacienteService;
import Clinica.proyecto.service.TipoCitaService;
import Clinica.proyecto.service.TratamientoService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping("/cita")
public class CitaController {

    @Autowired //Sirve para crear autoinstancias
    private CitaService citaService;

    @Autowired //Sirve para crear autoinstancias
    private MedicoService medicoService;

    @Autowired //Sirve para crear autoinstancias
    private TratamientoService tratamientoService;

    @Autowired //Sirve para crear autoinstancias
    private MedicamentosService medicamentoService;

    @Autowired //Sirve para crear autoinstancias
    private Cita_MedicamentoService citaMedicamentoService;

    @Autowired //Sirve para crear autoinstancias
    private Cita_TratamientoService citaTratamientoService;

    @Autowired //Sirve para crear autoinstancias
    private TipoCitaService tipoCitaService;

    @Autowired //Sirve para crear autoinstancias
    private PacienteService pacienteService;

    @GetMapping("/crudCita")
    public String inicio(Model model) {
        var tipoCitas = tipoCitaService.getTipoCitas();
        var medicos = medicoService.getMedicos();
        var pacientes = pacienteService.getPacientes();
        var citas = citaService.getCitas();

        model.addAttribute("citas", citas);
        model.addAttribute("tipoCitas", tipoCitas);
        model.addAttribute("medicos", medicos);
        model.addAttribute("pacientes", pacientes);
        return "/cita/crudCita";
    }

    @GetMapping("/modificar/{idCita}")
    public String citaModificar(Cita cita, Model model) {
        var tipoCitas = tipoCitaService.getTipoCitas();
        var medicos = medicoService.getMedicos();
        var pacientes = pacienteService.getPacientes();
        cita = citaService.getCita(cita);
        var medicamentos = medicamentoService.getMedicamentos();
        var tratamientos = tratamientoService.getTratamientos();
        var medicamentosCita = citaMedicamentoService.getCitasMedicamentos(cita.getIdCita());
        var tratamientoCita = citaTratamientoService.getCitaTratamientos(cita.getIdCita());
        model.addAttribute("cita", cita);
        model.addAttribute("tipoCitas", tipoCitas);
        model.addAttribute("medicos", medicos);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("medicamentos", medicamentos);
        model.addAttribute("medicamentosCita", medicamentosCita);
        model.addAttribute("tratamientos", tratamientos);
        model.addAttribute("tratamientoCita", tratamientoCita);
        return "/cita/modifica";
    }

    @GetMapping("/pagar/{idCita}")
    public String pago(Cita cita, Model model) {
        cita = citaService.getCita(cita);
        var medicamentosCita = citaMedicamentoService.getCitasMedicamentos(cita.getIdCita());
        var tratamientoCita = citaTratamientoService.getCitaTratamientos(cita.getIdCita());
        var total = 25000;
        var fecha = LocalDate.now();
        for (Tratamiento tratamiento : tratamientoCita) {
            total += tratamiento.getCosto();
        }
        
        for (Medicamentos medicamentos : medicamentosCita) {
            total += medicamentos.getCosto();
        }
        
        model.addAttribute("cita", cita);
        model.addAttribute("total", total);
        model.addAttribute("fecha", fecha);
        
        return "/pago/pago";
    }

    @PostMapping("/actualizarCita")
    public String citaActualizar(@ModelAttribute Cita cita, @RequestParam("medicamentosSeleccionados") String[] medicamentosSeleccionados, @RequestParam("tratamientosSeleccionados") String[] tratamientosSeleccionados) {
        citaService.save(cita, false);
        citaMedicamentoService.delete(cita);
        citaTratamientoService.delete(cita);
        for (String medicamentoId : medicamentosSeleccionados) {

            Medicamentos medicamento = new Medicamentos();
            medicamento.setCodigo_medicamento(Long.parseLong(medicamentoId));
            citaMedicamentoService.save(cita, medicamento);

        }

        for (String tratamientoId : tratamientosSeleccionados) {

            Tratamiento tratamiento = new Tratamiento();
            tratamiento.setID_Tratamiento(Long.parseLong(tratamientoId));
            citaTratamientoService.save(cita, tratamiento);

        }

        return "redirect:/cita/crudCita";
    }

    @GetMapping("/ver/{idCita}")
    public String citaVer(Cita cita, Model model) {
        cita = citaService.getCita(cita);
        var medicamentos = citaMedicamentoService.getCitasMedicamentos(cita.getIdCita());
        var tratamientos = citaTratamientoService.getCitaTratamientos(cita.getIdCita());
        model.addAttribute("cita", cita);
        model.addAttribute("medicamentos", medicamentos);
        model.addAttribute("tratamientos", tratamientos);
        System.out.println(tratamientos);
        return "/cita/ver";
    }

    @PostMapping("/guardarCita")
    public String citaGuardar(Cita cita) {
        citaService.save(cita, true);
        return "redirect:/cita/crudCita";
    }

    @GetMapping("/eliminar/{idCita}")
    public String citaEliminar(Cita cita) {
        citaService.delete(cita);
        citaMedicamentoService.delete(cita);
        citaTratamientoService.delete(cita);
        return "redirect:/cita/crudCita";
    }

}
