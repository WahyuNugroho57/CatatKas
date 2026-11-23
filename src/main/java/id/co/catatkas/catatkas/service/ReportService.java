
package id.co.catatkas.catatkas.service;

import id.co.catatkas.catatkas.repository.TransaksiRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.*;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final TransaksiRepository repository;

    public ReportService(TransaksiRepository repository) {
        this.repository = repository;
    }

    public byte[] cetakLaporan() throws JRException {

        // Membuat desain laporan
        JasperDesign desain = new JasperDesign();

        desain.setName("LaporanTransaksi");
        desain.setPageWidth(595);
        desain.setPageHeight(842);
        desain.setColumnWidth(535);
        desain.setLeftMargin(30);
        desain.setRightMargin(30);
        desain.setTopMargin(30);
        desain.setBottomMargin(30);

        // Menambahkan field dari model Transaksi
        tambahField(desain, "tanggal", LocalDate.class);
        tambahField(desain, "jenis", String.class);
        tambahField(desain, "kategori", String.class);
        tambahField(desain, "keterangan", String.class);
        tambahField(desain, "jumlah", BigDecimal.class);

        // Judul laporan
        JRDesignBand judul = new JRDesignBand();
        judul.setHeight(45);

        tambahTeks(judul, "LAPORAN DATA TRANSAKSI CATATKAS",
                0, 5, 535, 25);

        desain.setTitle(judul);

        // Header tabel
        JRDesignBand header = new JRDesignBand();
        header.setHeight(30);

        tambahTeks(header, "Tanggal", 0, 0, 75, 25);
        tambahTeks(header, "Jenis", 75, 0, 75, 25);
        tambahTeks(header, "Kategori", 150, 0, 85, 25);
        tambahTeks(header, "Keterangan", 235, 0, 190, 25);
        tambahTeks(header, "Jumlah", 425, 0, 110, 25);

        desain.setColumnHeader(header);

        // Baris data transaksi
        JRDesignBand detail = new JRDesignBand();
        detail.setHeight(25);

        tambahFieldTabel(detail, "tanggal", 
                "$F{tanggal}.format(java.time.format.DateTimeFormatter.ofPattern(\"dd-MM-yyyy\"))",
                0, 75);

        tambahFieldTabel(detail, "jenis",
                "$F{jenis}", 75, 75);

        tambahFieldTabel(detail, "kategori",
                "$F{kategori}", 150, 85);

        tambahFieldTabel(detail, "keterangan",
                "$F{keterangan}", 235, 190);

        tambahFieldTabel(detail, "jumlah",
                "\"Rp \" + new java.text.DecimalFormat(\"#,##0\").format($F{jumlah})",
                425, 110);

        JRDesignSection detailSection
                = (JRDesignSection) desain.getDetailSection();

        detailSection.addBand(detail);

        // Kompilasi desain
        JasperReport laporan =
                JasperCompileManager.compileReport(desain);

        // Mengambil data dari MySQL melalui repository
        JRBeanCollectionDataSource data =
                new JRBeanCollectionDataSource(repository.findAll());

        // Mengisi laporan
        JasperPrint hasil = JasperFillManager.fillReport(
                laporan,
                new HashMap<>(),
                data
        );

        // Mengubah laporan menjadi PDF
        return JasperExportManager.exportReportToPdf(hasil);
    }

    // Membuat field data
    private void tambahField(
            JasperDesign desain,
            String nama,
            Class<?> tipe
    ) throws JRException {

        JRDesignField field = new JRDesignField();
        field.setName(nama);
        field.setValueClass(tipe);

        desain.addField(field);
    }

    // Membuat tulisan statis
    private void tambahTeks(
            JRDesignBand band,
            String teks,
            int x,
            int y,
            int lebar,
            int tinggi
    ) {

        JRDesignStaticText tulisan = new JRDesignStaticText();

        tulisan.setX(x);
        tulisan.setY(y);
        tulisan.setWidth(lebar);
        tulisan.setHeight(tinggi);
        tulisan.setText(teks);

        band.addElement(tulisan);
    }

    // Membuat kolom yang mengambil data
    private void tambahFieldTabel(
            JRDesignBand band,
            String nama,
            String ekspresi,
            int x,
            int lebar
    ) {

        JRDesignTextField kolom = new JRDesignTextField();

        kolom.setX(x);
        kolom.setY(0);
        kolom.setWidth(lebar);
        kolom.setHeight(25);
        kolom.setBlankWhenNull(true);

        JRDesignExpression expression =
                new JRDesignExpression(ekspresi);

        kolom.setExpression(expression);

        band.addElement(kolom);
    }
}
