package buoi6;

public class TapChi extends TaiLieu {
    private static final long serialVersionUID = 1L;

    private int soPhatHanh;
    private int thangPhatHanh;
    private String chuDe;
    private String ngonNgu;

    public TapChi(String maTaiLieu, String tenTaiLieu, String tenNhaXuatBan,
                  int soBanPhatHanh, int soPhatHanh, int thangPhatHanh,
                  String chuDe, String ngonNgu) {
        super(maTaiLieu, tenTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.soPhatHanh = validSoDuong(soPhatHanh, "Số phát hành");
        setThangPhatHanh(thangPhatHanh);
        this.chuDe   = validChuoi(chuDe, "Chủ đề");
        this.ngonNgu = validChuoi(ngonNgu, "Ngôn ngữ");
    }

    // ===== Getters =====
    public int    getSoPhatHanh()    { return soPhatHanh; }
    public int    getThangPhatHanh() { return thangPhatHanh; }
    public String getChuDe()         { return chuDe; }
    public String getNgonNgu()       { return ngonNgu; }

    // ===== Setters (có validation) =====
    public void setSoPhatHanh(int soPhatHanh) { this.soPhatHanh = validSoDuong(soPhatHanh, "Số phát hành"); }
    public void setChuDe(String chuDe)        { this.chuDe      = validChuoi(chuDe, "Chủ đề"); }
    public void setNgonNgu(String ngonNgu)    { this.ngonNgu    = validChuoi(ngonNgu, "Ngôn ngữ"); }

    public void setThangPhatHanh(int thangPhatHanh) {
        if (thangPhatHanh < 1 || thangPhatHanh > 12) {
            throw new IllegalArgumentException("Tháng phát hành phải từ 1 đến 12!");
        }
        this.thangPhatHanh = thangPhatHanh;
    }

    @Override
    public String toThongTin() {
        return String.format(
            "[TẠP CHÍ] Mã: %-8s | Tên: %-25s | NXB: %-15s | Bản in: %4d | Số PH: %3d | Tháng: %2d | Chủ đề: %-12s | Ngôn ngữ: %s",
            getMaTaiLieu(), getTenTaiLieu(), getTenNhaXuatBan(),
            getSoBanPhatHanh(), soPhatHanh, thangPhatHanh, chuDe, ngonNgu);
    }
}