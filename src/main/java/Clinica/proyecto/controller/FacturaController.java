package Clinica.proyecto.controller;

import Clinica.proyecto.domain.Factura;
import Clinica.proyecto.service.FacturaService;
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
@RequestMapping("/factura")
public class FacturaController {

    @Autowired //Sirve para crear autoinstancias
    private FacturaService facturaService;

    @Autowired //Sirve para crear autoinstancias
    private PagoService pagoService;

    @GetMapping("/crudFactura")
    public String inicio(Model model) {
        var facturas = facturaService.getFacturas();
        var pagos = pagoService.getPagos();
        model.addAttribute("pagos", pagos);
        model.addAttribute("facturas", facturas);
        return "/factura/crudFactura";
    }

    @GetMapping("/modificar/{idFactura}")
    public String facturaModificar(Factura factura, Model model) {
        var pagos = pagoService.getPagos();
        model.addAttribute("pagos", pagos);
        var facturaObtenida = facturaService.getFactura(factura);
        model.addAttribute("factura", facturaObtenida);
        model.addAttribute("factura", factura);
        return "/factura/modifica";
    }

    @GetMapping("/ver/{idFactura}")
    public String facturaVer(Factura factura, Model model) {
        factura = facturaService.getFactura(factura);
        model.addAttribute("factura", factura);
        return "/factura/ver";
    }
    
    @PostMapping("/actualizarFactura")
    public String facturaActualizar(Factura factura, Long idFactura) {
        facturaService.save(factura, false, idFactura);
        return "redirect:/pago/crudFactura";
    }

    @PostMapping("/guardarFactura")
    public String facturaGuardar(Factura factura, Long idFactura) {
        facturaService.save(factura, true, null);
        return "redirect:/factura/crudFactura";
    }

    @GetMapping("/eliminar/{idFactura}")
    public String facturaEliminar(Factura factura) {
        facturaService.delete(factura);
        return "redirect:/factura/crudFactura";
    }

}
