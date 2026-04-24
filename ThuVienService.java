package buoi6;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.*;

/**
 * Service layer: xử lý toàn bộ nghiệp vụ quản lý tài liệu.
 * <ul>
 *   <li>CRUD: thêm / xóa / sửa</li>
 *   <li>Tìm kiếm & sắp xếp</li>
 *   <li>Thống kê</li>
 *   <li>Quản lý dirty flag (chưa lưu)</li>
 *   <li>Đọc/ghi qua {@link TaiLieuRepository}</li>
 * </ul>
 * Không chứa bất kỳ logic nhập/xuất màn hình nào.
 */
public class ThuVienService {

    private List<TaiLieu> danhSach;
    private boolean dirty;
    private final TaiLieuRepository repository;

    public ThuVienService(TaiLieuRepository repository) {
        this.repository = repository;
        this.danhSach   = new ArrayList<>();
        this.dirty      = false;
    }

    // ================================================================
    // Trạng thái
    // ================================================================

    /** Trả về true nếu có thay đổi chưa được lưu vào file. */
    public boolean isDirty() { return dirty; }

    // ================================================================
    // Khởi động
    // ================================================================

    /**
     * Tải dữ liệu từ file khi ứng dụng khởi động.
     * Nếu file không tồn tại hoặc đọc lỗi → giữ danh sách rỗng, không ném exception.
     *
     * @return thông báo kết quả để UI hiển thị
     */
    public String autoLoad() {
        try {
            List<TaiLieu> temp = repository.load();
            danhSach = temp;
            dirty    = false;
            if (danhSach.isEmpty()) {
                return "ℹ️  Chưa có file dữ liệu. Bắt đầu với danh sách trống.";
            }
            return "✅ Đã tải " + danhSach.size() + " tài liệu từ file: " + repository.getFileDatPath();
        } catch (IOException | ClassNotFoundException e) {
            return "⚠️  Không thể đọc file dữ liệu: " + e.getMessage()
                 + "\n   Bắt đầu với danh sách trống.";
        }
    }

    // ================================================================
    // CRUD
    // ================================================================

    /**
     * Thêm tài liệu mới vào danh sách.
     *
     * @param taiLieu tài liệu cần thêm
     * @throws MaTaiLieuTrungException nếu mã đã tồn tại
     */
    public void them(TaiLieu taiLieu) {
        if (findByMa(taiLieu.getMaTaiLieu()).isPresent()) {
            throw new MaTaiLieuTrungException(taiLieu.getMaTaiLieu());
        }
        danhSach.add(taiLieu);
        dirty = true;
    }

    /**
     * Xóa tài liệu theo mã.
     *
     * @param ma mã tài liệu cần xóa
     * @throws TaiLieuNotFoundException nếu mã không tồn tại
     */
    public void xoa(String ma) {
        TaiLieu tl = findByMa(ma)
                .orElseThrow(() -> new TaiLieuNotFoundException(ma));
        danhSach.remove(tl);
        dirty = true;
    }

    /**
     * Sửa tài liệu theo mã bằng cách áp dụng Consumer (Functional Interface).
     * Cho phép UI truyền vào một lambda/closure chứa toàn bộ logic cập nhật.
     *
     * @param ma      mã tài liệu cần sửa
     * @param updater hàm áp dụng thay đổi lên đối tượng
     * @throws TaiLieuNotFoundException nếu mã không tồn tại
     */
    public void sua(String ma, Consumer<TaiLieu> updater) {
        TaiLieu tl = findByMa(ma)
                .orElseThrow(() -> new TaiLieuNotFoundException(ma));
        updater.accept(tl);
        dirty = true;
    }

    // ================================================================
    // Truy vấn
    // ================================================================

    /** Trả về danh sách hiện tại (chỉ đọc). */
    public List<TaiLieu> getDanhSach() {
        return Collections.unmodifiableList(danhSach);
    }

    /** Tìm tài liệu theo mã (không phân biệt hoa/thường). */
    public Optional<TaiLieu> findByMa(String ma) {
        return danhSach.stream()
                .filter(tl -> tl.getMaTaiLieu().equalsIgnoreCase(ma))
                .findFirst();
    }

    /**
     * Lọc danh sách theo lớp cụ thể (sử dụng method reference {@code isInstance}).
     *
     * @param loai class cần lọc (Sach.class, TapChi.class, Bao.class)
     */
    public List<TaiLieu> findByLoai(Class<? extends TaiLieu> loai) {
        return danhSach.stream()
                .filter(loai::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * Tìm kiếm theo từ khóa trong tên và nhà xuất bản.
     * Sử dụng {@code Locale.ROOT} để tránh edge case locale.
     */
    public List<TaiLieu> findByTen(String tuKhoa) {
        String kw = tuKhoa.toLowerCase(Locale.ROOT);
        return danhSach.stream()
                .filter(tl -> tl.getTenTaiLieu().toLowerCase(Locale.ROOT).contains(kw)
                           || tl.getTenNhaXuatBan().toLowerCase(Locale.ROOT).contains(kw))
                .collect(Collectors.toList());
    }

    /**
     * Sắp xếp danh sách theo số bản phát hành giảm dần (in-place).
     *
     * @return view danh sách đã sắp xếp để UI hiển thị
     */
    public List<TaiLieu> sapXepTheoBanPhatHanh() {
        danhSach.sort(Comparator.comparingInt(TaiLieu::getSoBanPhatHanh).reversed());
        return Collections.unmodifiableList(danhSach);
    }

    // ================================================================
    // Thống kê
    // ================================================================

    /** Tổng số tài liệu trong danh sách. */
    public int tongSo() {
        return danhSach.size();
    }

    /** Số lượng tài liệu theo từng loại (Sách / Tạp chí / Báo). */
    public Map<String, Long> thongKeTheoLoai() {
        return danhSach.stream()
                .collect(Collectors.groupingBy(this::tenLoai, Collectors.counting()));
    }

    /** Thống kê số bản phát hành (min / max / trung bình). */
    public IntSummaryStatistics thongKeBanPhatHanh() {
        return danhSach.stream()
                .mapToInt(TaiLieu::getSoBanPhatHanh)
                .summaryStatistics();
    }

    /** Nhà xuất bản có nhiều tài liệu nhất. */
    public Optional<Map.Entry<String, Long>> nxbNhieuNhat() {
        return danhSach.stream()
                .collect(Collectors.groupingBy(TaiLieu::getTenNhaXuatBan, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    // ================================================================
    // Persistence
    // ================================================================

    /**
     * Lưu danh sách vào file.
     * Đặt dirty = false khi thành công.
     *
     * @throws IOException nếu có lỗi I/O
     */
    public void luuFile() throws IOException {
        repository.save(danhSach);
        dirty = false;
    }

    /**
     * Nạp lại dữ liệu từ file, thay thế danh sách trong RAM.
     * Sử dụng biến tạm để không làm mất dữ liệu cũ nếu đọc thất bại.
     * Đặt dirty = false khi thành công.
     *
     * @throws IOException            nếu có lỗi I/O
     * @throws ClassNotFoundException nếu dữ liệu không tương thích
     */
    public void napLaiFile() throws IOException, ClassNotFoundException {
        List<TaiLieu> temp = repository.load();   // đọc vào biến tạm trước
        danhSach = temp;                           // chỉ thay thế khi thành công
        dirty    = false;
    }

    /**
     * Xuất danh sách ra file văn bản.
     *
     * @throws IOException nếu có lỗi I/O
     */
    public void xuatTxt() throws IOException {
        repository.exportTxt(danhSach);
    }

    /** Đường dẫn file .dat (dùng để hiển thị trong UI). */
    public String getDatFilePath() {
        return repository.getFileDatPath();
    }

    /** Đường dẫn file .txt (dùng để hiển thị trong UI). */
    public String getTxtFilePath() {
        return repository.getFileTxtPath();
    }

    // ================================================================
    // Private helpers
    // ================================================================

    private String tenLoai(TaiLieu tl) {
        if (tl instanceof Sach)   return "Sách";
        if (tl instanceof TapChi) return "Tạp chí";
        if (tl instanceof Bao)    return "Báo";
        return "Khác";
    }
}
