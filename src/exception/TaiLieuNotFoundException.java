package buoi6;

/** Ném ra khi không tìm thấy tài liệu theo mã. */
public class TaiLieuNotFoundException extends RuntimeException {
    public TaiLieuNotFoundException(String maTaiLieu) {
        super("Không tìm thấy tài liệu có mã: " + maTaiLieu);
    }
}
