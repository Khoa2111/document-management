package buoi6;

import java.util.Scanner;

public class ThuvienApp {
    public static void main(String[] args) {
        QuanLyThuVien quanLy = new QuanLyThuVien();
        Scanner scanner = quanLy.getScanner();
        int choice;
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║       🎉 CHÀO MỪNG ĐẾN PHẦN MỀM QUẢN LÝ THƯ VIỆN 🎉         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        do {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    📚 MENU CHÍNH 📚                         ║");
            System.out.println("╠═══════════════════════════════════════════════════════════════╣");
            System.out.println("║ 1. ➕  Thêm tài liệu                                          ║");
            System.out.println("║ 2. 📖  Hiển thị danh sách                                     ║");
            System.out.println("║ 3. ❌  Xóa tài liệu                                           ║");
            System.out.println("║ 4. 🔍  Tìm kiếm theo loại                                     ║");
            System.out.println("║ 5. 📊  Sắp xếp danh sách                                      ║");
            System.out.println("║ 6. 💾  Ghi file                                               ║");
            System.out.println("║ 7. 📂  Đọc file                                               ║");
            System.out.println("║ 0. 🚪  Thoát chương trình                                     ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");
            System.out.print("👉 Chọn chức năng (0-7): ");
            
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    quanLy.themTaiLieu();
                    break;
                case 2:
                    quanLy.hienThiDanhSach();
                    break;
                case 3:
                    quanLy.xoaTaiLieu();
                    break;
                case 4:
                    quanLy.timKiemTheoLoai();
                    break;
                case 5:
                    quanLy.sapXepTheoBanPhatHanh();
                    break;
                case 6:
                    quanLy.ghiFile();
                    break;
                case 7:
                    quanLy.docFile();
                    break;
                case 0:
                    System.out.println("\n👋 Cảm ơn bạn đã sử dụng! Tạm biệt!");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        } while (choice != 0);
        
        scanner.close();
    }
}