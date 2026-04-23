package buoi6;

public class Sach extends TaiLieu {
    private static final long serialVersionUID = 1L;

    private String tenTacGia;
    private int soTrang;

    public Sach(String maTaiLieu, String tenTaiLieu, String tenNhaXuatBan,
                int soBanPhatHanh, String tenTacGia, int soTrang) {
        super(maTaiLieu, tenTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.tenTacGia = validChuoi(tenTacGia, "Tên tác giả");
        this.soTrang   = validSoDuong(soTrang, "Số trang");
    }

    // ===== Getters =====
    public String getTenTacGia() { return tenTacGia; }
    public int    getSoTrang()   { return soTrang; }

    // ===== Setters (có validation) =====
    public void setTenTacGia(String tenTacGia) { this.tenTacGia = validChuoi(tenTacGia, "Tên tác giả"); }
    public void setSoTrang(int soTrang)         { this.soTrang   = validSoDuong(soTrang, "Số trang"); }

    @Override
    public String toThongTin() {
        return String.format(
            "[SÁCH]    Mã: %-8s | Tên: %-25s | NXB: %-15s | Bản in: %4d | Tác giả: %-15s | Trang: %d",
            getMaTaiLieu(), getTenTaiLieu(), getTenNhaXuatBan(),
            getSoBanPhatHanh(), tenTacGia, soTrang);
    }
}