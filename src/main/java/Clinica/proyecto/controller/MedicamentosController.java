package Clinica.proyecto.controller;


import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.service.MedicamentosService;
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
@RequestMapping("/medicamentos")
public class MedicamentosController {

    @Autowired //Sirve para crear autoinstancias
    private MedicamentosService medicamentosService;
    
  

    @GetMapping("/crudMedicamentos")
    public String inicio(Model model) {
        var medicamentos = medicamentosService.getMedicamentos();
        model.addAttribute("medicamentos", medicamentos);
        return "/medicamentos/crudMedicamentos";
    }
    
    @PostMapping("/modificar")
    public String medicamentoModificar(Medicamentos medicamento) {
        medicamentosService.update(medicamento);
       return "redirect:/medicamentos/crudMedicamentos";
    }

    @GetMapping("/ver/{codigo_medicamento}")
    public String medicamentosVer(Medicamentos medicamentos, Model model) {
        medicamentos = medicamentosService.getMedicamentos(medicamentos);
        model.addAttribute("medicamentos", medicamentos);
        return "/medicamentos/ver";
    }

    @PostMapping("/guardarMedicamentos")
    public String medicamentosGuardar(Medicamentos medicamentos) {
        medicamentosService.save(medicamentos);
        return "redirect:/medicamentos/crudMedicamentos";
    }
    
    @GetMapping("/eliminar/{codigo_medicamento}")
    public String medicamentosEliminar(Medicamentos medicamentos) {
        System.out.println("Ayudaaaa");
        medicamentosService.delete(medicamentos);
        return "redirect:/medicamentos/crudMedicamentos";
    }
    
   
  @GetMapping("/actualizar/{codigo_medicamento}")
    public String actualizar(Medicamentos medicamentos, Model model) {
        medicamentos = medicamentosService.getMedicamentos(medicamentos);
        model.addAttribute("medicamentos", medicamentos);
        return "/medicamentos/modifica";
    }
}
