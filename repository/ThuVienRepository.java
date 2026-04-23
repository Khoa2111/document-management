package buoi6.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import buoi6.TaiLieu;

public class ThuVienRepository {
    // Giữ danh sách tài liệu trong RAM
    // Ghi file
    // Đọc file

    private List<TaiLieu> danhSach = new ArrayList<>();
    private static final String FILE_NAME = "thuvien.dat";  // hằng số

    // 1. getAll() Trả về toàn bộ danh sách.
    public List<TaiLieu> getAll() {
        return danhSach;
    }

    // 2. setAll(List) Cho phép load dữ liệu từ file vào RAM.
    public void setAll(List<TaiLieu> list) {
        this.danhSach = list;
    }

    // 3. saveToFile() -> ghi file
    public void saveToFile() throws IOException {

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Path.of(FILE_NAME)))) {
            oos.writeObject(danhSach);
        } 
    }

    // 4. loadFromFile() -> đọc file
    public void loadFromFile() throws IOException, ClassNotFoundException {

        // nếu file chưa tồn tại thì danh sách sẽ là rỗng, không cần đọc file
        if (Files.notExists(Path.of(FILE_NAME))) {
            danhSach = new ArrayList<>();
            return;
        }

    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Path.of(FILE_NAME)))) {
            List<TaiLieu> list = (List<TaiLieu>) ois.readObject();
            setAll(list);
    }
}
}
