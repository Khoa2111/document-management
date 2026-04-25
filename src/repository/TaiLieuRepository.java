package buoi6;

import java.io.IOException;
import java.util.List;

/**
 * Repository layer: định nghĩa hợp đồng cho việc đọc/lưu danh sách tài liệu
 * và xuất văn bản.
 */
public interface TaiLieuRepository {

    /**
     * Nạp danh sách tài liệu từ nơi lưu trữ.
     *
     * @return danh sách đã nạp (không null; rỗng nếu chưa có file)
     * @throws IOException            nếu có lỗi I/O
     * @throws ClassNotFoundException nếu dữ liệu không tương thích class
     */
    List<TaiLieu> load() throws IOException, ClassNotFoundException;

    /**
     * Lưu danh sách tài liệu xuống nơi lưu trữ.
     *
     * @param danhSach danh sách cần lưu
     * @throws IOException nếu có lỗi I/O
     */
    void save(List<TaiLieu> danhSach) throws IOException;

    /**
     * Xuất danh sách ra file văn bản.
     *
     * @param danhSach danh sách cần xuất
     * @throws IOException nếu có lỗi I/O
     */
    void exportTxt(List<TaiLieu> danhSach) throws IOException;

    /** Đường dẫn hiển thị của file nhị phân (.dat). */
    String getFileDatPath();

    /** Đường dẫn hiển thị của file văn bản (.txt). */
    String getFileTxtPath();
}
