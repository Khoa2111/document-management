package buoi6;

public class Sach extends TaiLieu {
    private static final long serialVersionUID = 1L;
    
    private String tenTacGia;
    private int soTrang;
    
    // Constructor
    public Sach(String maTaiLieu, String tenTaiLieu, String tenNhaXuatBan,
                int soBanPhatHanh, String tenTacGia, int soTrang) {
        super(maTaiLieu, tenTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.tenTacGia = tenTacGia;
        this.soTrang = soTrang;
    }
    
    // Getter & Setter
    public String getTenTacGia() {
        return tenTacGia;
    }
    
    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }
    
    public int getSoTrang() {
        return soTrang;
    }
    
    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }
    
    @Override
    public void hienThiThongTin() {
        System.out.printf("[SÁCH] Mã: %s | Tên sách: %s | NXB: %s | Bản in: %d | Tác giả: %s | Số trang: %d%n",
                getMaTaiLieu(), getTenTaiLieu(), getTenNhaXuatBan(),
                getSoBanPhatHanh(), tenTacGia, soTrang);
    }
}