package Clinica.proyecto.controller;

import Clinica.proyecto.domain.Cita;
import Clinica.proyecto.domain.Paciente;
import Clinica.proyecto.service.PacienteService;
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
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired //Sirve para crear autoinstancias
    private PacienteService pacienteService;

    @GetMapping("/crudPaciente")
    public String inicio(Model model) {
        var pacientes = pacienteService.getPacientes();
        model.addAttribute("pacientes", pacientes);
        return "/paciente/crudPaciente";
    }

    @GetMapping("/modificar/{cedula}")
    public String pacienteModificar(Paciente paciente, Model model) {
        var pacienteObtenido = pacienteService.getPaciente(paciente);
        model.addAttribute("paciente", pacienteObtenido);
        model.addAttribute("cedID", paciente.getCedula());
        return "/paciente/modifica";
    }

    @GetMapping("/ver/{cedula}")
    public String pacienteVer(Paciente paciente, Model model) {
        paciente = pacienteService.getPaciente(paciente);
        var citas = pacienteService.getCitas(paciente);
        var tratamientos = pacienteService.getTratamientos(citas);
        var medicamentos = pacienteService.getMedicamentos(citas);
        model.addAttribute("paciente", paciente);
        model.addAttribute("citas", citas);
        model.addAttribute("tratamientos", tratamientos);
        model.addAttribute("medicamentos", medicamentos);
        System.out.println(medicamentos);
        
        return "/paciente/ver";
    }

    @PostMapping("/actualizarPaciente")
    public String pacienteActualizar(Paciente paciente, Long cedID) {
        pacienteService.save(paciente, false, cedID);
        return "redirect:/paciente/crudPaciente";
    }

    @PostMapping("/guardarPaciente")
    public String pacienteGuardar(Paciente paciente) {
        pacienteService.save(paciente, true, null);
        return "redirect:/paciente/crudPaciente";
    }

    @GetMapping("/eliminar/{cedula}")
    public String pacienteEliminar(Paciente paciente) {
        pacienteService.delete(paciente);
        return "redirect:/paciente/crudPaciente";
    }

}
