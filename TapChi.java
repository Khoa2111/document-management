package buoi6;

public class TapChi extends TaiLieu {
    private static final long serialVersionUID = 1L;
    
    private int soPhatHanh;
    private int thangPhatHanh;
    private String chuDe;
    private String ngonNgu;
    
    // Constructor
    public TapChi(String maTaiLieu, String tenTaiLieu, String tenNhaXuatBan,
                  int soBanPhatHanh, int soPhatHanh, int thangPhatHanh,
                  String chuDe, String ngonNgu) {
        super(maTaiLieu, tenTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.soPhatHanh = soPhatHanh;
        this.thangPhatHanh = thangPhatHanh;
        this.chuDe = chuDe;
        this.ngonNgu = ngonNgu;
    }
    
    // Getter & Setter
    public int getSoPhatHanh() {
        return soPhatHanh;
    }
    
    public void setSoPhatHanh(int soPhatHanh) {
        this.soPhatHanh = soPhatHanh;
    }
    
    public int getThangPhatHanh() {
        return thangPhatHanh;
    }
    
    public void setThangPhatHanh(int thangPhatHanh) {
        this.thangPhatHanh = thangPhatHanh;
    }
    
    public String getChuDe() {
        return chuDe;
    }
    
    public void setChuDe(String chuDe) {
        this.chuDe = chuDe;
    }
    
    public String getNgonNgu() {
        return ngonNgu;
    }
    
    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }
    
    @Override
    public void hienThiThongTin() {
        System.out.printf("[TẠP CHÍ] Mã: %s | Tên tạp chí: %s | NXB: %s | Bản in: %d | Số PH: %d | Tháng: %d | Chủ đề: %s | Ngôn ngữ: %s%n",
                getMaTaiLieu(), getTenTaiLieu(), getTenNhaXuatBan(),
                getSoBanPhatHanh(), soPhatHanh, thangPhatHanh, chuDe, ngonNgu);
    }
}
