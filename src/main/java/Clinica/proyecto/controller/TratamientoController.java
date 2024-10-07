package Clinica.proyecto.controller;

import Clinica.proyecto.domain.Medicamentos;
import Clinica.proyecto.domain.Medico;
import Clinica.proyecto.domain.Tratamiento;
import Clinica.proyecto.domain.Tratamiento_Medicamento;
import Clinica.proyecto.service.MedicamentosService;
import Clinica.proyecto.service.TratamientoService;
import Clinica.proyecto.service.Tratamiento_MedicamentoService;
import Clinica.proyecto.serviceImpl.TratamientoServiceImpl;
import Clinica.proyecto.serviceImpl.Tratamiento_MedicamentoServiceImpl;
import java.util.ArrayList;
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
@RequestMapping("/tratamiento")
public class TratamientoController {

    @Autowired //Sirve para crear autoinstancias
    private TratamientoService tratamientoService;

    @Autowired //Sirve para crear autoinstancias
    private TratamientoServiceImpl tratamientoServiceimpl;
    @Autowired
    private Tratamiento_MedicamentoService tratamiento_medicamentoService;
    @Autowired
    private Tratamiento_MedicamentoServiceImpl tratamiento_medicamentoServiceimpl;

    @Autowired //Sirve para crear autoinstancias
    private MedicamentosService medicametosService;

    @GetMapping("/crudTratamiento")
    public String inicio(Model model) {
        var tratamiento = tratamientoService.getTratamientos();
        var medicamentos = medicametosService.getMedicamentos();
        model.addAttribute("tratamiento", tratamiento);
        model.addAttribute("medicamentos", medicamentos);
        return "/tratamiento/crudTratamiento";
    }

    @GetMapping("/modificar/{ID_Tratamiento}")
    public String tratamientoModificar(Tratamiento tratamiento, Model model) {
        var medicamentos = medicametosService.getMedicamentos();
        tratamiento = tratamientoService.getTratamiento(tratamiento);
        List<Tratamiento_Medicamento> T_medicamentos = tratamiento_medicamentoService.getTratamiento_Medicamentos(tratamiento);
        List<Medicamentos> medicamentosTratamiento = new ArrayList<>();
        for (Tratamiento_Medicamento medicamento : T_medicamentos) {
            long idMedicamento = medicamento.getIdMedicamento();
             Medicamentos med= new Medicamentos();
            med.setCodigo_medicamento(idMedicamento);
            Medicamentos medi = medicametosService.getMedicamentos(med);
            medicamentosTratamiento.add(medi);
        }
        model.addAttribute("tratamiento", tratamiento);
        model.addAttribute("medicamentosTratamiento", medicamentosTratamiento);
        model.addAttribute("medicamentos", medicamentos);
        return "/tratamiento/modifica";
    }

    @PostMapping("/actualizarTratamiento")
    public String actualizarTratamiento(@ModelAttribute Tratamiento tratamiento,@RequestParam("medicamentosSeleccionados") String[] medicamentosSeleccionados) {
        long idtratamiento = tratamiento.getID_Tratamiento();
        tratamientoService.save(tratamiento,false,null);
        tratamiento_medicamentoServiceimpl.delete(idtratamiento);
         for (String medicamentoId : medicamentosSeleccionados) {
                Long medicamentoIdLong = Long.parseLong(medicamentoId);
                tratamiento_medicamentoService.save(tratamiento.getID_Tratamiento(), medicamentoIdLong);
            }
        
        return "redirect:/tratamiento/crudTratamiento"; 
    }

    @GetMapping("/ver/{ID_Tratamiento}")
    public String tratamientoVer(Tratamiento tratamiento, Model model) {
        tratamiento = tratamientoService.getTratamiento(tratamiento);
        List<Tratamiento_Medicamento> T_medicamentos = tratamiento_medicamentoService.getTratamiento_Medicamentos(tratamiento);
        List<Medicamentos> medicamentos = new ArrayList<>();

        for (Tratamiento_Medicamento medicamento : T_medicamentos) {
            long idMedicamento = medicamento.getIdMedicamento();
            Medicamentos med= new Medicamentos();
            med.setCodigo_medicamento(idMedicamento);
            Medicamentos medi = medicametosService.getMedicamentos(med);
            medicamentos.add(medi);
        }

        model.addAttribute("tratamiento", tratamiento);
        model.addAttribute("medicamentos", medicamentos);
        return "/tratamiento/ver";
    }

    @PostMapping("/guardarTratamiento")
    public String tratamientoGuardar(@ModelAttribute Tratamiento tratamiento, @RequestParam("medicamentosSeleccionados") String[] medicamentosSeleccionados) {
        tratamientoService.save(tratamiento,true,null);
        String[] medicamentos = medicamentosSeleccionados;
        guardarMedicamentos(medicamentos);
        return "redirect:/tratamiento/crudTratamiento";
    }

    public void guardarMedicamentos(String[] Medicamentos) {
        try {
            Tratamiento ultimoTratamiento = tratamientoServiceimpl.UltimoTratamiento();
            long tratamientoId = ultimoTratamiento.getID_Tratamiento();

            for (String medicamentoId : Medicamentos) {
                Long medicamentoIdLong = Long.parseLong(medicamentoId);
                tratamiento_medicamentoService.save(tratamientoId, medicamentoIdLong);
            }
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    @GetMapping("/eliminar/{ID_Tratamiento}")
    public String tratamientoEliminar(Tratamiento tratamiento) {
        long idtratamiento = tratamiento.getID_Tratamiento();
        tratamiento_medicamentoService.delete(idtratamiento);
        tratamientoService.delete(tratamiento);
        return "redirect:/tratamiento/crudTratamiento";
    }

}
