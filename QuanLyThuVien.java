package buoi6;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import Inheritance.t;
import buoi6.service.ThuVienService;

public class QuanLyThuVien {            // UI - Giao diện người dùng
    private ThuVienService service;
    private Scanner scanner;
    
    QuanLyThuVien(ThuVienService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }
   
    // ===== CHỨC NĂNG 1: THÊM TÀI LIỆU =====
    public void themTaiLieu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        THÊM TÀI LIỆU MỚI               ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Sách                                ║");
        System.out.println("║ 2. Tạp chí                             ║");
        System.out.println("║ 3. Báo                                 ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Chọn loại (1-3): ");
        int loai = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Mã tài liệu: ");
        String maTaiLieu = scanner.nextLine();
        System.out.print("Tên tài liệu: ");
        String tenTaiLieu = scanner.nextLine();
        System.out.print("Nhà xuất bản: ");
        String tenNhaXuatBan = scanner.nextLine();
        System.out.print("Số bản phát hành: ");
        int soBanPhatHanh = scanner.nextInt();
        scanner.nextLine();

        TaiLieu tl = null;
        
        if (loai == 1) {  // Sách
            System.out.print("Tên tác giả: ");
            String tenTacGia = scanner.nextLine();
            System.out.print("Số trang: ");
            int soTrang = scanner.nextInt();

            tl = new Sach(maTaiLieu, tenTaiLieu, tenNhaXuatBan, soBanPhatHanh, tenTacGia, soTrang);
            
        } else if (loai == 2) {  // Tạp chí
            System.out.print("Số phát hành: ");
            int soPhatHanh = scanner.nextInt();
            System.out.print("Tháng phát hành (1-12): ");
            int thangPhatHanh = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Chủ đề: ");
            String chuDe = scanner.nextLine();
            System.out.print("Ngôn ngữ: ");
            String ngonNgu = scanner.nextLine();
            tl = new TapChi(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                    soBanPhatHanh, soPhatHanh, thangPhatHanh, chuDe, ngonNgu);
            
        } else if (loai == 3) { // Báo
            scanner.nextLine();
            System.out.print("Ngày phát hành (YYYY-MM-DD): ");
            String ngayPhatHanh = scanner.nextLine();
            System.out.print("Loại báo (Nhật báo/Tuần báo/...): ");
            String loaiBao = scanner.nextLine();
            System.out.print("Có trang màu? (true/false): ");
            boolean coTrangMau = scanner.nextBoolean();
            tl = new Bao(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                    soBanPhatHanh, ngayPhatHanh, loaiBao, coTrangMau);
        }
        if (tl != null) {
            boolean ok = service.addTaiLieu(tl);
            if (ok) {
                System.out.println("✅ Thêm tài liệu thành công!");
            } else {
                System.out.println("❌ mã tài liệu đã tồn tại!");
            }
        }
    }
    
    // ===== CHỨC NĂNG 2: HIỂN THỊ DANH SÁCH =====
    public void hienThiDanhSach() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           DANH SÁCH TẤT CẢ TÀI LIỆU                                    ║");
        System.out
                .println("╚════════════════════════════════════════════════════════════════════════════════════════╝");
        
        List<TaiLieu> list = service.getAll();
        if (list.isEmpty()) {
            System.out.println("⚠️  Danh sách trống!");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.print((i + 1) + ". ");
                list.get(i).hienThiThongTin();
            }
        }
    }
    
    // ===== CHỨC NĂNG 3: XÓA TÀI LIỆU =====
    public void xoaTaiLieu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         XÓA TÀI LIỆU                   ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (service.getAll().isEmpty()) {
            System.out.println("⚠️  Danh sách trống!");
            return;
        }
        
        System.out.print("Nhập mã tài liệu cần xóa: ");
        String maCanXoa = scanner.nextLine();
        
        boolean ok = service.deleteById(maCanXoa);
        System.out.println(ok ? "✅ Xóa tài liệu thanh cong!" : "❌ Xóa tài liệu khong thanh cong!");
        
    }
    
    // ===== CHỨC NĂNG 4: TÌM KIẾM THEO LOẠI =====
    public void timKiemTheoLoai() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      TÌM KIẾM THEO LOẠI TÀI LIỆU       ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Sách                                ║");
        System.out.println("║ 2. Tạp chí                             ║");
        System.out.println("║ 3. Báo                                 ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Chọn loại: ");
        int loai = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\n===== KẾT QUẢ TÌM KIẾM =====");
        
        Class<?> type = switch(loai) {
            case 1 -> Sach.class;
            case 2 -> TapChi.class;
            case 3 -> Bao.class;
            default -> null;
        };
        if (type == null) {
            System.out.println("❌ Lựa chọn không hợp lệ!");
            return;
        }

        service.filterByType(type).forEach(TaiLieu::hienThiThongTin);
    }
    
    // ===== CHỨC NĂNG 5: SẮP XẾP =====
    public void sapXepTheoBanPhatHanh() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  SẮP XẾP THEO SỐ BẢN PHÁT HÀNH (GIẢM DẦN)              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        if (service.getAll().isEmpty()) {
            System.out.println("⚠️  Danh sách trống!");
            return;
        }
        
        service.sortByBanPhatHanhDesc();
        System.out.println("✅ Đã sắp xếp danh sách (giảm dần)!\n");
        hienThiDanhSach();
    }
    
    // ===== CHỨC NĂNG 6: GHI FILE =====
    public void ghiFile() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         GHI DỮ LIỆU VÀO FILE          ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (service.getAll().isEmpty()) {
            System.out.println("⚠️  Danh sách trống! Không có gì để ghi.");
            return;
        }
        
        try {
            service.save();
            System.out.println("✅ Đã lưu dữ liệu thành công!");
            System.out.println("   📁 File: thuvien.dat");
            
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi ghi file: " + e.getMessage());
        }
    }
    
    // ===== CHỨC NĂNG 7: ĐỌC FILE =====
    public void docFile() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      ĐỌC DỮ LIỆU TỪ FILE              ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        try {
            service.load();
            System.out.println("✅ Đã nạp dữ liệu từ file thành công!");
            System.out.println("   📁 File: thuvien.dat");
            hienThiDanhSach();
               
            
        } catch (FileNotFoundException e) {
            System.out.println("❌ File chưa tồn tại!");
            System.out.println("   💡 Hãy thêm tài liệu và ghi file trước.");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi đọc file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Lỗi: Không tìm thấy lớp " + e.getMessage());
        }
    }
    
    // Getter
    public Scanner getScanner() {
        return scanner;
    }
}
