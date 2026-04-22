package buoi6;

public class Bao extends TaiLieu {
    private static final long serialVersionUID = 1L;
    
    private String ngayPhatHanh;
    private String loaiBao;
    private boolean coTrangMau;
    
    // Constructor
    public Bao(String maTaiLieu, String tenTaiLieu, String tenNhaXuatBan,
               int soBanPhatHanh, String ngayPhatHanh, String loaiBao,
               boolean coTrangMau) {
        super(maTaiLieu, tenTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.ngayPhatHanh = ngayPhatHanh;
        this.loaiBao = loaiBao;
        this.coTrangMau = coTrangMau;
    }
    
    // Getter & Setter
    public String getNgayPhatHanh() {
        return ngayPhatHanh;
    }
    
    public void setNgayPhatHanh(String ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }
    
    public String getLoaiBao() {
        return loaiBao;
    }
    
    public void setLoaiBao(String loaiBao) {
        this.loaiBao = loaiBao;
    }
    
    public boolean isCoTrangMau() {
        return coTrangMau;
    }
    
    public void setCoTrangMau(boolean coTrangMau) {
        this.coTrangMau = coTrangMau;
    }
    
    @Override
    public void hienThiThongTin() {
        String hinhThuc = coTrangMau ? "Màu" : "Đen-trắng";
        System.out.printf("[BÁO] Mã: %s | Tên báo: %s | NXB: %s | Bản in: %d | Ngày PH: %s | Loại: %s | Hình thức: %s%n",
                getMaTaiLieu(), getTenTaiLieu(), getTenNhaXuatBan(),
                getSoBanPhatHanh(), ngayPhatHanh, loaiBao, hinhThuc);
    }
}
