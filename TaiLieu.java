package buoi6;

import java.io.Serializable;
import java.util.Objects;

public abstract class TaiLieu implements Serializable {
    private static final long serialVersionUID = 1L;

    private String maTaiLieu;
    private String tenTaiLieu;
    private String tenNhaXuatBan;
    private int soBanPhatHanh;

    public TaiLieu(String maTaiLieu, String tenTaiLieu,
                   String tenNhaXuatBan, int soBanPhatHanh) {
        this.maTaiLieu     = validChuoi(maTaiLieu, "Mã tài liệu");
        this.tenTaiLieu    = validChuoi(tenTaiLieu, "Tên tài liệu");
        this.tenNhaXuatBan = validChuoi(tenNhaXuatBan, "Nhà xuất bản");
        this.soBanPhatHanh = validSoDuong(soBanPhatHanh, "Số bản phát hành");
    }

    // ===== Validation helpers (dùng chung cho các lớp con) =====
    protected static String validChuoi(String value, String tenTruong) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(tenTruong + " không được để trống!");
        }
        return value.trim();
    }

    protected static int validSoDuong(int value, String tenTruong) {
        if (value <= 0) {
            throw new IllegalArgumentException(tenTruong + " phải là số dương!");
        }
        return value;
    }

    // ===== Getters =====
    public String getMaTaiLieu()     { return maTaiLieu; }
    public String getTenTaiLieu()    { return tenTaiLieu; }
    public String getTenNhaXuatBan() { return tenNhaXuatBan; }
    public int    getSoBanPhatHanh() { return soBanPhatHanh; }

    // ===== Setters (có validation) =====
    public void setMaTaiLieu(String maTaiLieu)         { this.maTaiLieu     = validChuoi(maTaiLieu, "Mã tài liệu"); }
    public void setTenTaiLieu(String tenTaiLieu)       { this.tenTaiLieu    = validChuoi(tenTaiLieu, "Tên tài liệu"); }
    public void setTenNhaXuatBan(String tenNhaXuatBan) { this.tenNhaXuatBan = validChuoi(tenNhaXuatBan, "Nhà xuất bản"); }
    public void setSoBanPhatHanh(int soBanPhatHanh)    { this.soBanPhatHanh = validSoDuong(soBanPhatHanh, "Số bản phát hành"); }

    /**
     * Trả về chuỗi thông tin đầy đủ của tài liệu.
     * Dùng để in ra màn hình và xuất file văn bản (NIO).
     */
    public abstract String toThongTin();

    /** In thông tin ra màn hình – delegate xuống toThongTin(). */
    public void hienThiThongTin() {
        System.out.println(toThongTin());
    }

    /** Hai tài liệu bằng nhau khi có cùng mã (không phân biệt hoa/thường). */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TaiLieu other)) return false;
        return maTaiLieu.equalsIgnoreCase(other.maTaiLieu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTaiLieu.toLowerCase());
    }

    @Override
    public String toString() {
        return toThongTin();
    }
}