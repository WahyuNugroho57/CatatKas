
package id.co.catatkas.catatkas.controller;

import id.co.catatkas.catatkas.service.ReportService;

import net.sf.jasperreports.engine.JRException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/laporan/transaksi/pdf")
    public ResponseEntity<byte[]> cetakPDF() throws JRException {

        byte[] pdf = reportService.cetakLaporan();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=laporan_transaksi.pdf"
                )
                .body(pdf);
    }
}
