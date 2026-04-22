package buoi6;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class QuanLyThuVien {
    private ArrayList<TaiLieu> danhSach;
    private Scanner scanner;
    
    // Constructor
    public QuanLyThuVien() {
        danhSach = new ArrayList<>();
        scanner = new Scanner(System.in);
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
        
        if (loai == 1) {  // Sách
            System.out.print("Tên tác giả: ");
            String tenTacGia = scanner.nextLine();
            System.out.print("Số trang: ");
            int soTrang = scanner.nextInt();
            danhSach.add(new Sach(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                  soBanPhatHanh, tenTacGia, soTrang));
            System.out.println("✅ Thêm sách thành công!");
            
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
            danhSach.add(new TapChi(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                    soBanPhatHanh, soPhatHanh, thangPhatHanh,
                                    chuDe, ngonNgu));
            System.out.println("✅ Thêm tạp chí thành công!");
            
        } else if (loai == 3) {  // Báo
            scanner.nextLine();
            System.out.print("Ngày phát hành (YYYY-MM-DD): ");
            String ngayPhatHanh = scanner.nextLine();
            System.out.print("Loại báo (Nhật báo/Tuần báo/...): ");
            String loaiBao = scanner.nextLine();
            System.out.print("Có trang màu? (true/false): ");
            boolean coTrangMau = scanner.nextBoolean();
            danhSach.add(new Bao(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                 soBanPhatHanh, ngayPhatHanh, loaiBao, coTrangMau));
            System.out.println("✅ Thêm báo thành công!");
        } else {
            System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }
    
    // ===== CHỨC NĂNG 2: HIỂN THỊ DANH SÁCH =====
    public void hienThiDanhSach() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           DANH SÁCH TẤT CẢ TÀI LIỆU                                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════╝");
        
        if (danhSach.isEmpty()) {
            System.out.println("⚠️  Danh sách trống!");
        } else {
            for (int i = 0; i < danhSach.size(); i++) {
                System.out.print((i + 1) + ". ");
                danhSach.get(i).hienThiThongTin();
            }
        }
    }
    
    // ===== CHỨC NĂNG 3: XÓA TÀI LIỆU =====
    public void xoaTaiLieu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         XÓA TÀI LIỆU                   ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (danhSach.isEmpty()) {
            System.out.println("⚠️  Danh sách trống!");
            return;
        }
        
        System.out.print("Nhập mã tài liệu cần xóa: ");
        String maCanXoa = scanner.nextLine();
        
        boolean timThay = false;
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaTaiLieu().equals(maCanXoa)) {
                TaiLieu xoa = danhSach.remove(i);
                System.out.println("✅ Đã xóa tài liệu: " + xoa.getTenTaiLieu());
                timThay = true;
                break;
            }
        }
        
        if (!timThay) {
            System.out.println("❌ Không tìm thấy tài liệu có mã: " + maCanXoa);
        }
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
        boolean timThay = false;
        int stt = 1;
        
        for (TaiLieu tl : danhSach) {
            if ((loai == 1 && tl instanceof Sach) ||
                (loai == 2 && tl instanceof TapChi) ||
                (loai == 3 && tl instanceof Bao)) {
                System.out.print(stt + ". ");
                tl.hienThiThongTin();
                stt++;
                timThay = true;
            }
        }
        
        if (!timThay) {
            System.out.println("⚠️  Không tìm thấy tài liệu loại này!");
        }
    }
    
    // ===== CHỨC NĂNG 5: SẮP XẾP =====
    public void sapXepTheoBanPhatHanh() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║  SẮP XẾP THEO SỐ BẢN PHÁT HÀNH (GIẢM DẦN)              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        if (danhSach.isEmpty()) {
            System.out.println("⚠️  Danh sách trống!");
            return;
        }
        
        Collections.sort(danhSach, new Comparator<TaiLieu>() {
            @Override
            public int compare(TaiLieu o1, TaiLieu o2) {
                // Giảm dần: o2 - o1
                return o2.getSoBanPhatHanh() - o1.getSoBanPhatHanh();
            }
        });
        
        System.out.println("✅ Đã sắp xếp danh sách (giảm dần)!\n");
        hienThiDanhSach();
    }
    
    // ===== CHỨC NĂNG 6: GHI FILE =====
    public void ghiFile() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         GHI DỮ LIỆU VÀO FILE          ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (danhSach.isEmpty()) {
            System.out.println("⚠️  Danh sách trống! Không có gì để ghi.");
            return;
        }
        
        try {
            FileOutputStream fos = new FileOutputStream("thuvien.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(danhSach);
            
            oos.close();
            fos.close();
            
            System.out.println("✅ Đã lưu dữ liệu thành công!");
            System.out.println("   📁 File: thuvien.dat");
            System.out.println("   📊 Số tài liệu: " + danhSach.size());
            
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
            FileInputStream fis = new FileInputStream("thuvien.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            @SuppressWarnings("unchecked")
            ArrayList<TaiLieu> danhSachDoc = (ArrayList<TaiLieu>) ois.readObject();
            
            ois.close();
            fis.close();
            
            danhSach = danhSachDoc;
            
            System.out.println("✅ Đã nạp " + danhSach.size() + " tài liệu từ file!");
            System.out.println("   📁 File: thuvien.dat\n");
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
