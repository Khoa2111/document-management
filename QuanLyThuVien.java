package buoi6;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.*;

public class QuanLyThuVien {

    private static final String FILE_DAT = "thuvien.dat";
    private static final String FILE_TXT = "thuvien.txt";

    private List<TaiLieu> danhSach;
    private final Scanner scanner;
    private boolean dirty = false;

    public QuanLyThuVien() {
        danhSach = new ArrayList<>();
        scanner  = new Scanner(System.in);
    }

    // =====================================================================
    // ENTRY POINT – vòng lặp menu chính
    // =====================================================================
    public void chay() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║       🎉 CHÀO MỪNG ĐẾN PHẦN MỀM QUẢN LÝ THƯ VIỆN 🎉         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        autoLoadFile();

        int luaChon;
        do {
            inMenu();
            luaChon = nhapSoNguyen("👉 Chọn chức năng (0-11): ", 0, 11);

            switch (luaChon) {
                case  1 -> themTaiLieu();
                case  2 -> hienThiDanhSach();
                case  3 -> xoaTaiLieu();
                case  4 -> suaTaiLieu();
                case  5 -> timKiemTheoLoai();
                case  6 -> timKiemTheoTen();
                case  7 -> sapXepTheoBanPhatHanh();
                case  8 -> thongKe();
                case  9 -> ghiFile();
                case 10 -> docFile();
                case 11 -> xuatRaFileTxt();
                case  0 -> System.out.println("\n👋 Cảm ơn bạn đã sử dụng! Tạm biệt!");
                default -> System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        } while (luaChon != 0);

        scanner.close();
    }

    private void inMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    📚 MENU CHÍNH 📚                          ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        if (dirty) {
            System.out.println("║  ⚠️  [CHƯA LƯU] Có thay đổi chưa được lưu vào file!        ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
        }
        System.out.println("║  1. ➕  Thêm tài liệu                                        ║");
        System.out.println("║  2. 📖  Hiển thị danh sách                                   ║");
        System.out.println("║  3. ❌  Xóa tài liệu                                         ║");
        System.out.println("║  4. ✏️   Sửa tài liệu                                         ║");
        System.out.println("║  5. 🔍  Tìm kiếm theo loại                                   ║");
        System.out.println("║  6. 🔎  Tìm kiếm theo tên                                    ║");
        System.out.println("║  7. 📊  Sắp xếp theo số bản phát hành                        ║");
        System.out.println("║  8. 📈  Thống kê tổng hợp                                    ║");
        System.out.println("║  9. 💾  Ghi file nhị phân (.dat)                              ║");
        System.out.println("║ 10. 📂  Đọc file nhị phân (.dat)                              ║");
        System.out.println("║ 11. 📝  Xuất file văn bản (.txt)                              ║");
        System.out.println("║  0. 🚪  Thoát chương trình                                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    // =====================================================================
    // CHỨC NĂNG 1 – THÊM TÀI LIỆU
    // =====================================================================
    public void themTaiLieu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        THÊM TÀI LIỆU MỚI              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Sách                                ║");
        System.out.println("║ 2. Tạp chí                             ║");
        System.out.println("║ 3. Báo                                 ║");
        System.out.println("╚════════════════════════════════════════╝");
        int loai = nhapSoNguyen("Chọn loại (1-3): ", 1, 3);

        String maTaiLieu = nhapChuoi("Mã tài liệu   : ");
        try {
            kiemTraMaTrung(maTaiLieu);
        } catch (MaTaiLieuTrungException e) {
            System.out.println("❌ " + e.getMessage());
            return;
        }

        String tenTaiLieu    = nhapChuoi("Tên tài liệu  : ");
        String tenNhaXuatBan = nhapChuoi("Nhà xuất bản  : ");
        int soBanPhatHanh    = nhapSoNguyen("Số bản phát hành (> 0): ", 1, Integer.MAX_VALUE);

        try {
            switch (loai) {
                case 1 -> {
                    String tenTacGia = nhapChuoi("Tên tác giả: ");
                    int soTrang      = nhapSoNguyen("Số trang (> 0): ", 1, Integer.MAX_VALUE);
                    danhSach.add(new Sach(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                         soBanPhatHanh, tenTacGia, soTrang));
                }
                case 2 -> {
                    int soPhatHanh    = nhapSoNguyen("Số phát hành (> 0): ", 1, Integer.MAX_VALUE);
                    int thangPhatHanh = nhapSoNguyen("Tháng phát hành (1-12): ", 1, 12);
                    String chuDe      = nhapChuoi("Chủ đề   : ");
                    String ngonNgu    = nhapChuoi("Ngôn ngữ : ");
                    danhSach.add(new TapChi(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                           soBanPhatHanh, soPhatHanh, thangPhatHanh,
                                           chuDe, ngonNgu));
                }
                case 3 -> {
                    LocalDate ngayPhatHanh = nhapNgay("Ngày phát hành (YYYY-MM-DD): ");
                    String loaiBao         = nhapChuoi("Loại báo (Nhật báo/Tuần báo/...): ");
                    boolean coTrangMau     = nhapBoolean("Có trang màu?");
                    danhSach.add(new Bao(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                        soBanPhatHanh, ngayPhatHanh, loaiBao, coTrangMau));
                }
            }
            System.out.println("✅ Thêm tài liệu thành công!");
            dirty = true;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Dữ liệu không hợp lệ: " + e.getMessage());
        }
    }

    // =====================================================================
    // CHỨC NĂNG 2 – HIỂN THỊ DANH SÁCH  (Stream + forEach)
    // =====================================================================
    public void hienThiDanhSach() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                 DANH SÁCH TẤT CẢ TÀI LIỆU                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");

        if (danhSach.isEmpty()) {
            System.out.println("⚠️  Danh sách trống!");
            return;
        }

        // Dùng IntStream để có số thứ tự
        IntStream.range(0, danhSach.size())
                 .forEach(i -> System.out.println((i + 1) + ". " + danhSach.get(i).toThongTin()));
    }

    // =====================================================================
    // CHỨC NĂNG 3 – XÓA TÀI LIỆU  (Optional)
    // =====================================================================
    public void xoaTaiLieu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║            XÓA TÀI LIỆU               ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (danhSach.isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        String ma = nhapChuoi("Mã tài liệu cần xóa: ");

        // timTheoMa trả về Optional – nếu có thì xóa, không thì báo lỗi
        timTheoMa(ma).ifPresentOrElse(
            tl -> {
                danhSach.remove(tl);
                System.out.println("✅ Đã xóa: " + tl.getTenTaiLieu());
                dirty = true;
            },
            () -> System.out.println("❌ Không tìm thấy tài liệu có mã: " + ma)
        );
    }

    // =====================================================================
    // CHỨC NĂNG 4 – SỬA TÀI LIỆU  (Optional)
    // =====================================================================
    public void suaTaiLieu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           SỬA TÀI LIỆU                ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (danhSach.isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        String ma  = nhapChuoi("Mã tài liệu cần sửa: ");
        Optional<TaiLieu> opt = timTheoMa(ma);

        if (opt.isEmpty()) {
            System.out.println("❌ Không tìm thấy tài liệu có mã: " + ma);
            return;
        }

        TaiLieu tl = opt.get();
        System.out.println("📄 Thông tin hiện tại:");
        tl.hienThiThongTin();
        System.out.println("\n(Nhấn Enter để giữ nguyên giá trị cũ)");

        // --- Cập nhật các trường chung ---
        String tenMoi = nhapTuyChon("Tên tài liệu mới [" + tl.getTenTaiLieu() + "]: ");
        if (!tenMoi.isEmpty()) tl.setTenTaiLieu(tenMoi);

        String nxbMoi = nhapTuyChon("Nhà xuất bản mới [" + tl.getTenNhaXuatBan() + "]: ");
        if (!nxbMoi.isEmpty()) tl.setTenNhaXuatBan(nxbMoi);

        String soBanStr = nhapTuyChon("Số bản phát hành mới [" + tl.getSoBanPhatHanh() + "]: ");
        if (!soBanStr.isEmpty()) {
            try {
                tl.setSoBanPhatHanh(Integer.parseInt(soBanStr));
            } catch (IllegalArgumentException e) {
                System.out.println("  ⚠️  Giá trị không hợp lệ, giữ nguyên.");
            }
        }

        // --- Cập nhật trường riêng theo loại (pattern matching – Java 17) ---
        if (tl instanceof Sach sach) {
            String tacGiaMoi = nhapTuyChon("Tên tác giả mới [" + sach.getTenTacGia() + "]: ");
            if (!tacGiaMoi.isEmpty()) sach.setTenTacGia(tacGiaMoi);

            String soTrangStr = nhapTuyChon("Số trang mới [" + sach.getSoTrang() + "]: ");
            if (!soTrangStr.isEmpty()) {
                try { sach.setSoTrang(Integer.parseInt(soTrangStr)); }
                catch (IllegalArgumentException e) {
                    System.out.println("  ⚠️  Giá trị không hợp lệ, giữ nguyên.");
                }
            }
        } else if (tl instanceof TapChi tapChi) {
            String chuDeMoi  = nhapTuyChon("Chủ đề mới [" + tapChi.getChuDe() + "]: ");
            if (!chuDeMoi.isEmpty()) tapChi.setChuDe(chuDeMoi);

            String ngonNguMoi = nhapTuyChon("Ngôn ngữ mới [" + tapChi.getNgonNgu() + "]: ");
            if (!ngonNguMoi.isEmpty()) tapChi.setNgonNgu(ngonNguMoi);
        } else if (tl instanceof Bao bao) {
            String ngayStr = nhapTuyChon("Ngày phát hành mới [" + bao.getNgayPhatHanh() + "] (YYYY-MM-DD): ");
            if (!ngayStr.isEmpty()) {
                try { bao.setNgayPhatHanh(LocalDate.parse(ngayStr)); }
                catch (DateTimeParseException e) { System.out.println("  ⚠️  Ngày không hợp lệ, giữ nguyên."); }
            }
        }

        System.out.println("✅ Đã cập nhật tài liệu thành công!");
        dirty = true;
        tl.hienThiThongTin();
    }

    // =====================================================================
    // CHỨC NĂNG 5 – TÌM KIẾM THEO LOẠI  (Stream + filter + instanceof)
    // =====================================================================
    public void timKiemTheoLoai() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      TÌM KIẾM THEO LOẠI TÀI LIỆU      ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Sách    2. Tạp chí    3. Báo        ║");
        System.out.println("╚════════════════════════════════════════╝");
        int loai = nhapSoNguyen("Chọn loại (1-3): ", 1, 3);

        // Xây dựng Predicate dựa trên lựa chọn
        List<TaiLieu> ketQua = danhSach.stream()
                .filter(tl -> switch (loai) {
                    case 1 -> tl instanceof Sach;
                    case 2 -> tl instanceof TapChi;
                    case 3 -> tl instanceof Bao;
                    default -> false;
                })
                .collect(Collectors.toList());

        inKetQua(ketQua);
    }

    // =====================================================================
    // CHỨC NĂNG 6 – TÌM KIẾM THEO TÊN  (Stream + filter + contains)
    // =====================================================================
    public void timKiemTheoTen() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       TÌM KIẾM THEO TÊN TÀI LIỆU      ║");
        System.out.println("╚════════════════════════════════════════╝");

        String tuKhoa = nhapChuoi("Nhập từ khóa: ").toLowerCase();

        List<TaiLieu> ketQua = danhSach.stream()
                .filter(tl -> tl.getTenTaiLieu().toLowerCase().contains(tuKhoa)
                           || tl.getTenNhaXuatBan().toLowerCase().contains(tuKhoa))
                .collect(Collectors.toList());

        System.out.println("🔎 Từ khóa: \"" + tuKhoa + "\"");
        inKetQua(ketQua);
    }

    // =====================================================================
    // CHỨC NĂNG 7 – SẮP XẾP  (Comparator lambda + Stream.sorted)
    // =====================================================================
    public void sapXepTheoBanPhatHanh() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║    SẮP XẾP THEO SỐ BẢN PHÁT HÀNH (GIẢM DẦN)               ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        if (danhSach.isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        // Comparator viết bằng lambda (method reference + reversed)
        danhSach.sort(Comparator.comparingInt(TaiLieu::getSoBanPhatHanh).reversed());

        System.out.println("✅ Đã sắp xếp (giảm dần)!\n");
        hienThiDanhSach();
    }

    // =====================================================================
    // CHỨC NĂNG 8 – THỐNG KÊ  (Collectors.groupingBy + IntSummaryStatistics)
    // =====================================================================
    public void thongKe() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         THỐNG KÊ TỔNG HỢP              ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (danhSach.isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        // Gom nhóm theo tên lớp → đếm số lượng
        Map<String, Long> theoLoai = danhSach.stream()
                .collect(Collectors.groupingBy(
                    tl -> tenLoai(tl),
                    Collectors.counting()
                ));

        // Thống kê số bản phát hành
        IntSummaryStatistics stats = danhSach.stream()
                .mapToInt(TaiLieu::getSoBanPhatHanh)
                .summaryStatistics();

        // NXB có nhiều tài liệu nhất
        Optional<Map.Entry<String, Long>> nxbNhieu = danhSach.stream()
                .collect(Collectors.groupingBy(TaiLieu::getTenNhaXuatBan, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());

        System.out.println("📊 Tổng số tài liệu : " + danhSach.size());
        System.out.println("   Phân loại:");
        theoLoai.forEach((loai, so) ->
            System.out.printf("   %-10s: %d tài liệu%n", loai, so));

        System.out.println("📈 Số bản phát hành:");
        System.out.printf("   Cao nhất   : %d%n", stats.getMax());
        System.out.printf("   Thấp nhất  : %d%n", stats.getMin());
        System.out.printf("   Trung bình : %.1f%n", stats.getAverage());

        nxbNhieu.ifPresent(e ->
            System.out.printf("🏆 NXB nhiều tài liệu nhất: %s (%d)%n", e.getKey(), e.getValue()));
    }

    // =====================================================================
    // CHỨC NĂNG 9 – GHI FILE NHỊ PHÂN  (Serialization + try-with-resources)
    // =====================================================================
    public void ghiFile() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       GHI DỮ LIỆU VÀO FILE (.dat)     ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (danhSach.isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        try (ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(FILE_DAT))) {
            oos.writeObject(danhSach);
            dirty = false;
            System.out.println("✅ Đã lưu " + danhSach.size() + " tài liệu vào: " + FILE_DAT);
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // =====================================================================
    // CHỨC NĂNG 10 – ĐỌC FILE NHỊ PHÂN  (Serialization + try-with-resources)
    // =====================================================================
    public void docFile() {
        if (!dirty) {
            thucHienDocFile();
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  ⚠️  CÓ DỮ LIỆU CHƯA LƯU – CHỌN HÀNH ĐỘNG:              ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. 💾  Lưu rồi đọc lại  (Save then reload)               ║");
        System.out.println("║  2. 🗑️   Bỏ thay đổi và đọc lại  (Discard and reload)      ║");
        System.out.println("║  0. ↩️   Hủy  (Cancel)                                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        int chon = nhapSoNguyen("👉 Chọn (0-2): ", 0, 2);

        switch (chon) {
            case 1 -> {
                ghiFile();
                if (!dirty) {   // chỉ tải lại nếu ghi thành công
                    thucHienDocFile();
                } else {
                    System.out.println("⚠️  Lưu thất bại, không tải lại file.");
                }
            }
            case 2 -> thucHienDocFile();
            case 0 -> System.out.println("↩️  Đã hủy.");
        }
    }

    @SuppressWarnings("unchecked")
    private void thucHienDocFile() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       ĐỌC DỮ LIỆU TỪ FILE (.dat)      ║");
        System.out.println("╚════════════════════════════════════════╝");

        try (ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(FILE_DAT))) {
            List<TaiLieu> temp = (List<TaiLieu>) ois.readObject();
            danhSach = temp;
            dirty = false;
            System.out.println("✅ Đã nạp " + danhSach.size() + " tài liệu từ: " + FILE_DAT);
            hienThiDanhSach();
        } catch (FileNotFoundException e) {
            System.out.println("❌ File chưa tồn tại! Hãy ghi file trước (chức năng 9).");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi đọc file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Lỗi tương thích class: " + e.getMessage());
        }
    }

    // =====================================================================
    // CHỨC NĂNG 11 – XUẤT FILE VĂN BẢN  (NIO – Files.write)
    // =====================================================================
    public void xuatRaFileTxt() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    XUẤT DANH SÁCH RA FILE VĂN BẢN     ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (danhSach.isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        // Tạo nội dung bằng Stream
        List<String> lines = new ArrayList<>();
        String header = "===== THƯ VIỆN – DANH SÁCH TÀI LIỆU =====";
        lines.add(header);
        lines.add("Ngày xuất : " + LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lines.add("Tổng số   : " + danhSach.size() + " tài liệu");
        lines.add("=".repeat(header.length()));

        List<String> docLines = IntStream.range(0, danhSach.size())
                .mapToObj(i -> (i + 1) + ". " + danhSach.get(i).toThongTin())
                .collect(Collectors.toList());
        lines.addAll(docLines);

        Path path = Path.of(FILE_TXT);
        try {
            Files.write(path, lines, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("✅ Đã xuất " + danhSach.size() + " tài liệu ra: "
                               + path.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi xuất file: " + e.getMessage());
        }
    }

    // =====================================================================
    // PRIVATE HELPERS
    // =====================================================================

    /** Tự động nạp dữ liệu từ file khi khởi động (nếu file tồn tại). */
    @SuppressWarnings("unchecked")
    private void autoLoadFile() {
        File file = new File(FILE_DAT);
        if (!file.exists()) {
            System.out.println("ℹ️  Chưa có file dữ liệu. Bắt đầu với danh sách trống.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<TaiLieu> temp = (List<TaiLieu>) ois.readObject();
            danhSach = temp;
            dirty = false;
            System.out.println("✅ Đã tải " + danhSach.size() + " tài liệu từ file: " + FILE_DAT);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️  Không thể đọc file dữ liệu: " + e.getMessage());
            System.out.println("   Bắt đầu với danh sách trống.");
        }
    }

    /** Tìm tài liệu theo mã, trả về Optional (không phân biệt hoa/thường). */
    private Optional<TaiLieu> timTheoMa(String ma) {
        return danhSach.stream()
                       .filter(tl -> tl.getMaTaiLieu().equalsIgnoreCase(ma))
                       .findFirst();
    }

    /** Ném MaTaiLieuTrungException nếu mã đã tồn tại. */
    private void kiemTraMaTrung(String ma) {
        if (timTheoMa(ma).isPresent()) {
            throw new MaTaiLieuTrungException(ma);
        }
    }

    /** In danh sách kết quả tìm kiếm. */
    private void inKetQua(List<TaiLieu> ketQua) {
        System.out.println("\n===== KẾT QUẢ (" + ketQua.size() + " tài liệu) =====");
        if (ketQua.isEmpty()) {
            System.out.println("⚠️  Không tìm thấy tài liệu phù hợp!");
        } else {
            IntStream.range(0, ketQua.size())
                     .forEach(i -> System.out.println((i + 1) + ". " + ketQua.get(i).toThongTin()));
        }
    }

    // ----- Input helpers -----

    /** Nhập chuỗi, không cho trống. */
    private String nhapChuoi(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("  ⚠️  Không được để trống!");
        }
    }

    /** Nhập chuỗi tùy chọn (cho phép Enter bỏ qua khi sửa). */
    private String nhapTuyChon(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /** Nhập số nguyên trong khoảng [min, max], tự động nhắc lại nếu sai. */
    private int nhapSoNguyen(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(line);
                if (val >= min && val <= max) return val;
                System.out.printf("  ⚠️  Vui lòng nhập số từ %d đến %d!%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }

    /** Nhập ngày theo định dạng ISO (YYYY-MM-DD). */
    private LocalDate nhapNgay(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return LocalDate.parse(line);
            } catch (DateTimeParseException e) {
                System.out.println("  ⚠️  Định dạng ngày không hợp lệ! Vui lòng nhập YYYY-MM-DD.");
            }
        }
    }

    /** Nhập yes/no (y/n). */
    private boolean nhapBoolean(String prompt) {
        Set<String> yes = Set.of("y", "yes", "co", "có");
        Set<String> no  = Set.of("n", "no",  "khong", "không");
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String val = scanner.nextLine().trim().toLowerCase();
            if (yes.contains(val)) return true;
            if (no.contains(val))  return false;
            System.out.println("  ⚠️  Nhập 'y' (có) hoặc 'n' (không)!");
        }
    }

    /** Trả về tên loại tài liệu hiển thị cho người dùng. */
    private String tenLoai(TaiLieu tl) {
        if (tl instanceof Sach)   return "Sách";
        if (tl instanceof TapChi) return "Tạp chí";
        if (tl instanceof Bao)    return "Báo";
        return "Khác";
    }
}
