package buoi6;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * UI layer: menu, nhập liệu và hiển thị kết quả trên console.
 * Mọi nghiệp vụ đều delegate xuống {@link ThuVienService}.
 */
public class QuanLyThuVien {

    private final ThuVienService service;
    private final Scanner scanner;

    public QuanLyThuVien(ThuVienService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    // =====================================================================
    // ENTRY POINT – vòng lặp menu chính
    // =====================================================================
    public void chay() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║       🎉 CHÀO MỪNG ĐẾN PHẦN MỀM QUẢN LÝ THƯ VIỆN 🎉         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        System.out.println(service.autoLoad());

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
        if (service.isDirty()) {
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

        String maTaiLieu     = nhapChuoi("Mã tài liệu   : ");
        String tenTaiLieu    = nhapChuoi("Tên tài liệu  : ");
        String tenNhaXuatBan = nhapChuoi("Nhà xuất bản  : ");
        int soBanPhatHanh    = nhapSoNguyen("Số bản phát hành (> 0): ", 1, Integer.MAX_VALUE);

        try {
            TaiLieu taiLieu = switch (loai) {
                case 1 -> {
                    String tenTacGia = nhapChuoi("Tên tác giả: ");
                    int soTrang      = nhapSoNguyen("Số trang (> 0): ", 1, Integer.MAX_VALUE);
                    yield new Sach(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                   soBanPhatHanh, tenTacGia, soTrang);
                }
                case 2 -> {
                    int soPhatHanh    = nhapSoNguyen("Số phát hành (> 0): ", 1, Integer.MAX_VALUE);
                    int thangPhatHanh = nhapSoNguyen("Tháng phát hành (1-12): ", 1, 12);
                    String chuDe      = nhapChuoi("Chủ đề   : ");
                    String ngonNgu    = nhapChuoi("Ngôn ngữ : ");
                    yield new TapChi(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                     soBanPhatHanh, soPhatHanh, thangPhatHanh, chuDe, ngonNgu);
                }
                default -> {
                    LocalDate ngayPhatHanh = nhapNgay("Ngày phát hành (YYYY-MM-DD): ");
                    String loaiBao         = nhapChuoi("Loại báo (Nhật báo/Tuần báo/...): ");
                    boolean coTrangMau     = nhapBoolean("Có trang màu?");
                    yield new Bao(maTaiLieu, tenTaiLieu, tenNhaXuatBan,
                                  soBanPhatHanh, ngayPhatHanh, loaiBao, coTrangMau);
                }
            };
            service.them(taiLieu);
            System.out.println("✅ Thêm tài liệu thành công!");
        } catch (MaTaiLieuTrungException e) {
            System.out.println("❌ " + e.getMessage());
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

        List<TaiLieu> ds = service.getDanhSach();
        if (ds.isEmpty()) {
            System.out.println("⚠️  Danh sách trống!");
            return;
        }
        IntStream.range(0, ds.size())
                 .forEach(i -> System.out.println((i + 1) + ". " + ds.get(i).toThongTin()));
    }

    // =====================================================================
    // CHỨC NĂNG 3 – XÓA TÀI LIỆU
    // =====================================================================
    public void xoaTaiLieu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║            XÓA TÀI LIỆU               ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (service.getDanhSach().isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        String ma = nhapChuoi("Mã tài liệu cần xóa: ");
        try {
            service.xoa(ma);
            System.out.println("✅ Đã xóa tài liệu có mã: " + ma);
        } catch (TaiLieuNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // =====================================================================
    // CHỨC NĂNG 4 – SỬA TÀI LIỆU  (Optional + Consumer Functional Interface)
    // =====================================================================
    public void suaTaiLieu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           SỬA TÀI LIỆU                ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (service.getDanhSach().isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        String ma = nhapChuoi("Mã tài liệu cần sửa: ");
        Optional<TaiLieu> opt = service.findByMa(ma);

        if (opt.isEmpty()) {
            System.out.println("❌ Không tìm thấy tài liệu có mã: " + ma);
            return;
        }

        TaiLieu tl = opt.get();
        System.out.println("📄 Thông tin hiện tại:");
        tl.hienThiThongTin();
        System.out.println("\n(Nhấn Enter để giữ nguyên giá trị cũ)");

        // --- Nhập các trường chung ---
        final String fTen   = nhapTuyChon("Tên tài liệu mới [" + tl.getTenTaiLieu() + "]: ");
        final String fNxb   = nhapTuyChon("Nhà xuất bản mới [" + tl.getTenNhaXuatBan() + "]: ");
        final String sBan   = nhapTuyChon("Số bản phát hành mới [" + tl.getSoBanPhatHanh() + "]: ");
        final Integer fBan  = parseIntOrNull(sBan);
        if (!sBan.isEmpty() && fBan == null) {
            System.out.println("  ⚠️  Giá trị không hợp lệ, giữ nguyên.");
        }

        // --- Nhập trường riêng theo loại (Java 21 pattern matching) ---
        final String  fTacGia;
        final Integer fSoTrang;
        final String  fChuDe;
        final String  fNgonNgu;
        final LocalDate fNgay;

        if (tl instanceof Sach sach) {
            fTacGia  = nhapTuyChon("Tên tác giả mới [" + sach.getTenTacGia() + "]: ");
            String s = nhapTuyChon("Số trang mới [" + sach.getSoTrang() + "]: ");
            fSoTrang = parseIntOrNull(s);
            if (!s.isEmpty() && fSoTrang == null) System.out.println("  ⚠️  Giá trị không hợp lệ, giữ nguyên.");
            fChuDe = ""; fNgonNgu = ""; fNgay = null;
        } else if (tl instanceof TapChi tapChi) {
            fChuDe   = nhapTuyChon("Chủ đề mới [" + tapChi.getChuDe() + "]: ");
            fNgonNgu = nhapTuyChon("Ngôn ngữ mới [" + tapChi.getNgonNgu() + "]: ");
            fTacGia = ""; fSoTrang = null; fNgay = null;
        } else if (tl instanceof Bao bao) {
            String s = nhapTuyChon("Ngày phát hành mới [" + bao.getNgayPhatHanh() + "] (YYYY-MM-DD): ");
            fNgay = parseDateOrNull(s);
            if (!s.isEmpty() && fNgay == null) System.out.println("  ⚠️  Ngày không hợp lệ, giữ nguyên.");
            fTacGia = ""; fSoTrang = null; fChuDe = ""; fNgonNgu = "";
        } else {
            fTacGia = ""; fSoTrang = null; fChuDe = ""; fNgonNgu = ""; fNgay = null;
        }

        // --- Áp dụng tất cả thay đổi qua Consumer<TaiLieu> ---
        try {
            service.sua(ma, t -> {
                if (!fTen.isEmpty())  t.setTenTaiLieu(fTen);
                if (!fNxb.isEmpty())  t.setTenNhaXuatBan(fNxb);
                if (fBan != null)     t.setSoBanPhatHanh(fBan);
                if (t instanceof Sach sach) {
                    if (!fTacGia.isEmpty()) sach.setTenTacGia(fTacGia);
                    if (fSoTrang != null)   sach.setSoTrang(fSoTrang);
                } else if (t instanceof TapChi tapChi) {
                    if (!fChuDe.isEmpty())   tapChi.setChuDe(fChuDe);
                    if (!fNgonNgu.isEmpty()) tapChi.setNgonNgu(fNgonNgu);
                } else if (t instanceof Bao bao) {
                    if (fNgay != null) bao.setNgayPhatHanh(fNgay);
                }
            });
            System.out.println("✅ Đã cập nhật tài liệu thành công!");
            service.findByMa(ma).ifPresent(TaiLieu::hienThiThongTin);
        } catch (TaiLieuNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // =====================================================================
    // CHỨC NĂNG 5 – TÌM KIẾM THEO LOẠI  (Stream + filter + method reference)
    // =====================================================================
    public void timKiemTheoLoai() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      TÌM KIẾM THEO LOẠI TÀI LIỆU      ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Sách    2. Tạp chí    3. Báo        ║");
        System.out.println("╚════════════════════════════════════════╝");
        int chon = nhapSoNguyen("Chọn loại (1-3): ", 1, 3);

        Class<? extends TaiLieu> loaiClass = switch (chon) {
            case 1 -> Sach.class;
            case 2 -> TapChi.class;
            default -> Bao.class;
        };
        inKetQua(service.findByLoai(loaiClass));
    }

    // =====================================================================
    // CHỨC NĂNG 6 – TÌM KIẾM THEO TÊN  (Stream + filter + contains)
    // =====================================================================
    public void timKiemTheoTen() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       TÌM KIẾM THEO TÊN TÀI LIỆU      ║");
        System.out.println("╚════════════════════════════════════════╝");

        String tuKhoa = nhapChuoi("Nhập từ khóa: ");
        List<TaiLieu> ketQua = service.findByTen(tuKhoa);
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

        if (service.getDanhSach().isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        List<TaiLieu> sorted = service.sapXepTheoBanPhatHanh();
        System.out.println("✅ Đã sắp xếp (giảm dần)!\n");
        inKetQua(sorted);
    }

    // =====================================================================
    // CHỨC NĂNG 8 – THỐNG KÊ  (Collectors.groupingBy + IntSummaryStatistics)
    // =====================================================================
    public void thongKe() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         THỐNG KÊ TỔNG HỢP              ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (service.getDanhSach().isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        Map<String, Long> theoLoai = service.thongKeTheoLoai();
        IntSummaryStatistics stats = service.thongKeBanPhatHanh();

        System.out.println("📊 Tổng số tài liệu : " + service.tongSo());
        System.out.println("   Phân loại:");
        theoLoai.forEach((loai, so) ->
            System.out.printf("   %-10s: %d tài liệu%n", loai, so));

        System.out.println("📈 Số bản phát hành:");
        System.out.printf("   Cao nhất   : %d%n", stats.getMax());
        System.out.printf("   Thấp nhất  : %d%n", stats.getMin());
        System.out.printf("   Trung bình : %.1f%n", stats.getAverage());

        service.nxbNhieuNhat().ifPresent(e ->
            System.out.printf("🏆 NXB nhiều tài liệu nhất: %s (%d)%n", e.getKey(), e.getValue()));
    }

    // =====================================================================
    // CHỨC NĂNG 9 – GHI FILE NHỊ PHÂN  (Serialization + try-with-resources)
    // =====================================================================
    public void ghiFile() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       GHI DỮ LIỆU VÀO FILE (.dat)     ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (service.getDanhSach().isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        try {
            service.luuFile();
            System.out.println("✅ Đã lưu " + service.tongSo() + " tài liệu vào: "
                               + service.getDatFilePath());
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // =====================================================================
    // CHỨC NĂNG 10 – ĐỌC FILE NHỊ PHÂN  (safe load với dirty check)
    // =====================================================================
    public void docFile() {
        if (!service.isDirty()) {
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
                if (!service.isDirty()) {   // chỉ tải lại nếu ghi thành công
                    thucHienDocFile();
                } else {
                    System.out.println("⚠️  Lưu thất bại, không tải lại file.");
                }
            }
            case 2 -> thucHienDocFile();
            case 0 -> System.out.println("↩️  Đã hủy.");
        }
    }

    private void thucHienDocFile() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       ĐỌC DỮ LIỆU TỪ FILE (.dat)      ║");
        System.out.println("╚════════════════════════════════════════╝");

        try {
            service.napLaiFile();
            System.out.println("✅ Đã nạp " + service.tongSo() + " tài liệu từ: "
                               + service.getDatFilePath());
            hienThiDanhSach();
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

        if (service.getDanhSach().isEmpty()) { System.out.println("⚠️  Danh sách trống!"); return; }

        try {
            service.xuatTxt();
            System.out.println("✅ Đã xuất " + service.tongSo() + " tài liệu ra: "
                               + service.getTxtFilePath());
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi xuất file: " + e.getMessage());
        }
    }

    // =====================================================================
    // PRIVATE HELPERS
    // =====================================================================

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
            String val = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (yes.contains(val)) return true;
            if (no.contains(val))  return false;
            System.out.println("  ⚠️  Nhập 'y' (có) hoặc 'n' (không)!");
        }
    }

    /** Parse số nguyên, trả về null nếu chuỗi rỗng hoặc không hợp lệ. */
    private Integer parseIntOrNull(String s) {
        if (s.isEmpty()) return null;
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { return null; }
    }

    /** Parse ngày, trả về null nếu chuỗi rỗng hoặc không hợp lệ. */
    private LocalDate parseDateOrNull(String s) {
        if (s.isEmpty()) return null;
        try { return LocalDate.parse(s); }
        catch (DateTimeParseException e) { return null; }
    }
}
