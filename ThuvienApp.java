package buoi6;

public class ThuvienApp {
    public static void main(String[] args) {
        TaiLieuRepository repository = new BinaryTaiLieuRepository();
        ThuVienService     service    = new ThuVienService(repository);
        new QuanLyThuVien(service).chay();
    }
}