package buoi6;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Bao extends TaiLieu {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter HIEN_THI = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocalDate ngayPhatHanh;
    private String loaiBao;
    private boolean coTrangMau;

    public Bao(String maTaiLieu, String tenTaiLieu, String tenNhaXuatBan,
               int soBanPhatHanh, LocalDate ngayPhatHanh, String loaiBao,
               boolean coTrangMau) {
        super(maTaiLieu, tenTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.ngayPhatHanh = Objects.requireNonNull(ngayPhatHanh, "Ngày phát hành không được null!");
        this.loaiBao      = validChuoi(loaiBao, "Loại báo");
        this.coTrangMau   = coTrangMau;
    }

    // ===== Getters =====
    public LocalDate getNgayPhatHanh() { return ngayPhatHanh; }
    public String    getLoaiBao()      { return loaiBao; }
    public boolean   isCoTrangMau()    { return coTrangMau; }

    // ===== Setters (có validation) =====
    public void setNgayPhatHanh(LocalDate ngayPhatHanh) {
        this.ngayPhatHanh = Objects.requireNonNull(ngayPhatHanh, "Ngày phát hành không được null!");
    }
    public void setLoaiBao(String loaiBao)        { this.loaiBao    = validChuoi(loaiBao, "Loại báo"); }
    public void setCoTrangMau(boolean coTrangMau) { this.coTrangMau = coTrangMau; }

    @Override
    public String toThongTin() {
        return String.format(
            "[BÁO]     Mã: %-8s | Tên: %-25s | NXB: %-15s | Bản in: %4d | Ngày PH: %s | Loại: %-12s | %s",
            getMaTaiLieu(), getTenTaiLieu(), getTenNhaXuatBan(),
            getSoBanPhatHanh(), ngayPhatHanh.format(HIEN_THI), loaiBao,
            coTrangMau ? "Màu" : "Đen-trắng");
    }
}