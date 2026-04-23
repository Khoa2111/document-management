package buoi6;

/** Ném ra khi cố thêm tài liệu có mã đã tồn tại trong danh sách. */
public class MaTaiLieuTrungException extends RuntimeException {
    public MaTaiLieuTrungException(String maTaiLieu) {
        super("Mã tài liệu đã tồn tại: " + maTaiLieu);
    }
}
