package buoi6;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Lưu trữ danh sách tài liệu bằng Java Serialization ({@code .dat})
 * và xuất văn bản bằng NIO ({@code .txt}).
 * Mọi file được đặt trong thư mục {@code data/}.
 */
public class BinaryTaiLieuRepository implements TaiLieuRepository {

    private static final Path DATA_DIR = Path.of("data");
    private static final Path FILE_DAT = DATA_DIR.resolve("thuvien.dat");
    private static final Path FILE_TXT = DATA_DIR.resolve("thuvien.txt");

    /** Tạo thư mục data/ nếu chưa tồn tại. */
    private void ensureDataDir() throws IOException {
        Files.createDirectories(DATA_DIR);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TaiLieu> load() throws IOException, ClassNotFoundException {
        if (!Files.exists(FILE_DAT)) {
            return new ArrayList<>();   // chưa có file → danh sách rỗng
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(Files.newInputStream(FILE_DAT)))) {
            List<TaiLieu> loaded = (List<TaiLieu>) ois.readObject();
            return loaded != null ? loaded : new ArrayList<>();
        }
    }

    @Override
    public void save(List<TaiLieu> danhSach) throws IOException {
        ensureDataDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(Files.newOutputStream(FILE_DAT,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING)))) {
            oos.writeObject(new ArrayList<>(danhSach));
        }
    }

    @Override
    public void exportTxt(List<TaiLieu> danhSach) throws IOException {
        ensureDataDir();
        List<String> lines = new ArrayList<>();
        String header = "===== THƯ VIỆN – DANH SÁCH TÀI LIỆU =====";
        lines.add(header);
        lines.add("Ngày xuất : " + LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lines.add("Tổng số   : " + danhSach.size() + " tài liệu");
        lines.add("=".repeat(header.length()));
        IntStream.range(0, danhSach.size())
                 .mapToObj(i -> (i + 1) + ". " + danhSach.get(i).toThongTin())
                 .forEach(lines::add);
        Files.write(FILE_TXT, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    public String getFileDatPath() {
        return FILE_DAT.toAbsolutePath().toString();
    }

    @Override
    public String getFileTxtPath() {
        return FILE_TXT.toAbsolutePath().toString();
    }
}
