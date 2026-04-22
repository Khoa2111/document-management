package buoi6;

import java.io.Serializable;

public abstract class TaiLieu implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String maTaiLieu;
    private String tenTaiLieu;
    private String tenNhaXuatBan;
    private int soBanPhatHanh;
    
    // Constructor
    public TaiLieu(String maTaiLieu, String tenTaiLieu, 
                   String tenNhaXuatBan, int soBanPhatHanh) {
        this.maTaiLieu = maTaiLieu;
        this.tenTaiLieu = tenTaiLieu;
        this.tenNhaXuatBan = tenNhaXuatBan;
        this.soBanPhatHanh = soBanPhatHanh;
    }
    
    // Getter & Setter
    public String getMaTaiLieu() {
        return maTaiLieu;
    }
    
    public void setMaTaiLieu(String maTaiLieu) {
        this.maTaiLieu = maTaiLieu;
    }
    
    public String getTenTaiLieu() {
        return tenTaiLieu;
    }
    
    public void setTenTaiLieu(String tenTaiLieu) {
        this.tenTaiLieu = tenTaiLieu;
    }
    
    public String getTenNhaXuatBan() {
        return tenNhaXuatBan;
    }
    
    public void setTenNhaXuatBan(String tenNhaXuatBan) {
        this.tenNhaXuatBan = tenNhaXuatBan;
    }
    
    public int getSoBanPhatHanh() {
        return soBanPhatHanh;
    }
    
    public void setSoBanPhatHanh(int soBanPhatHanh) {
        this.soBanPhatHanh = soBanPhatHanh;
    }
    
    // Phương thức trừu tượng
    public abstract void hienThiThongTin();
}