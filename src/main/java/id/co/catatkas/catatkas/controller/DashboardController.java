package id.co.catatkas.catatkas.controller;
import id.co.catatkas.catatkas.model.Transaksi;
import id.co.catatkas.catatkas.repository.TransaksiRepository;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller
public class DashboardController {
    private final TransaksiRepository repository;
    public DashboardController(TransaksiRepository repository){this.repository=repository;}
    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("transaksiList",repository.findAll());
        model.addAttribute("transaksi",new Transaksi());
        model.addAttribute("totalPemasukan",total("Pemasukan"));
        model.addAttribute("totalPengeluaran",total("Pengeluaran"));
        return "dashboard";
    }
    @PostMapping("/transaksi/simpan")
    public String simpan(@Valid @ModelAttribute("transaksi") Transaksi transaksi, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("transaksiList",repository.findAll());
            model.addAttribute("totalPemasukan",total("Pemasukan"));
            model.addAttribute("totalPengeluaran",total("Pengeluaran"));
            return "dashboard";
        }
        repository.save(transaksi);
        return "redirect:/";
    }
    @PostMapping("/transaksi/hapus/{id}")
    public String hapus(@PathVariable Long id){repository.deleteById(id);return "redirect:/";}
    private BigDecimal total(String jenis){
        return repository.findAll().stream().filter(t->jenis.equalsIgnoreCase(t.getJenis()))
          .map(Transaksi::getJumlah).filter(j->j!=null).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
