package Clinica.proyecto.controller;

import Clinica.proyecto.domain.Especialidad;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.service.EspecialidadService;
import Clinica.proyecto.service.MedicoService;
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
@RequestMapping("/medico")
public class MedicoController {

    @Autowired //Sirve para crear autoinstancias
    private MedicoService medicoService;
    
    @Autowired //Sirve para crear autoinstancias
    private EspecialidadService especialidadService;

    @GetMapping("/crudMedico")
    public String inicio(Model model) {
        var medicos = medicoService.getMedicos();
        var especialidades = especialidadService.getEspecialidades();
        model.addAttribute("medicos", medicos);
        model.addAttribute("especialidades", especialidades);
        return "/medico/crudMedico";
    }
    
    @GetMapping("/modificar/{cedula}")
    public String medicoModificar(Medico medico, Model model) {
        var especialidades = especialidadService.getEspecialidades();
        model.addAttribute("especialidades", especialidades);
        var medicoObtenido = medicoService.getMedico(medico);
        System.out.println(medicoObtenido);
        model.addAttribute("medico", medicoObtenido);
        model.addAttribute("cedID", medico.getCedula());
        return "/medico/modifica";
    }

    @GetMapping("/ver/{cedula}")
    public String medicoVer(Medico medico, Model model) {
        medico = medicoService.getMedico(medico);
        model.addAttribute("medico", medico);
        return "/medico/ver";
    }
    
    @PostMapping("/actualizarMedico")
    public String medicoActualizar(Medico medico, Long cedID) {
        System.out.println(medico);
        medicoService.save(medico, false, cedID);
        return "redirect:/medico/crudMedico";
    }

    @PostMapping("/guardarMedico")
    public String medicoGuardar(Medico medico) {
        medicoService.save(medico, true, null);
        return "redirect:/medico/crudMedico";
    }
    
    @GetMapping("/eliminar/{cedula}")
    public String medicoEliminar(Medico medico) {
        medicoService.delete(medico);
        return "redirect:/medico/crudMedico";
    }

}
