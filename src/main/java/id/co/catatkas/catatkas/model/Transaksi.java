package id.co.catatkas.catatkas.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name="transaksi")
public class Transaksi {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotNull(message="Tanggal harus diisi") private LocalDate tanggal;
    @NotBlank(message="Jenis harus dipilih") private String jenis;
    @NotBlank(message="Kategori harus diisi") private String kategori;
    @NotBlank(message="Keterangan harus diisi") @Column(length=500) private String keterangan;
    @NotNull(message="Jumlah harus diisi") @DecimalMin(value="0.01", message="Jumlah harus lebih dari 0") private BigDecimal jumlah;
    public Transaksi() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public LocalDate getTanggal(){return tanggal;} public void setTanggal(LocalDate tanggal){this.tanggal=tanggal;}
    public String getJenis(){return jenis;} public void setJenis(String jenis){this.jenis=jenis;}
    public String getKategori(){return kategori;} public void setKategori(String kategori){this.kategori=kategori;}
    public String getKeterangan(){return keterangan;} public void setKeterangan(String keterangan){this.keterangan=keterangan;}
    public BigDecimal getJumlah(){return jumlah;} public void setJumlah(BigDecimal jumlah){this.jumlah=jumlah;}
}
