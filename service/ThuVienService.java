package buoi6.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import buoi6.Bao;
import buoi6.Sach;
import buoi6.TaiLieu;
import buoi6.TapChi;
import buoi6.repository.ThuVienRepository;

public class ThuVienService {
    private final ThuVienRepository repo;

    public ThuVienService(ThuVienRepository repo) {
        this.repo = repo;
    }

    // 1. addTaiLieu()
    // Yêu cầu:
    // Không cho trùng mã tài liệu
    // Phải dùng Stream
    // Trả về boolean (thêm thành công hay không)

    public boolean addTaiLieu(TaiLieu tl) {
        // check trùng 
        boolean ex = repo.getAll().stream()
                .anyMatch(s -> s.getMaTaiLieu().equalsIgnoreCase(tl.getMaTaiLieu()));
        if (ex) {
            return false;
        }
        repo.getAll().add(tl);
        return true;
    }

    // 2. getAll() => Chỉ gọi repo.getAll(), chỉ cần lấy còn việc in ra là của UI
    public List<TaiLieu> getAll() {
        return repo.getAll();
    }

    // 3. findById() trả về Optional<TaiLieu>
    public Optional<TaiLieu> findById(String id) {
        return repo.getAll().stream()
                .filter(s -> s.getMaTaiLieu().equalsIgnoreCase(id)).findFirst();
    }

    // 4. deleteById() trả về boolean (xóa thành công hay không)
    public boolean deleteById(String id) {
        // tìm id đó trước
        Optional<TaiLieu> opt = findById(id);
        opt.ifPresent(repo.getAll()::remove);
        return opt.isPresent();
    }

    // 5. searchByName() trả về List<TaiLieu>, => keyword search.
    public List<TaiLieu> searchByName(String name) {
        return repo.getAll().stream()
                .filter(s -> s.getTenTaiLieu().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    // 6. filterByType() trả về List<TaiLieu>, tìm kiếm theo loại tài liệu (sách, tạp chí, báo)
    public List<TaiLieu> filterByType(Class<?> type) {
        return repo.getAll().stream()
                .filter(type::isInstance).toList();
    }

    // 7. sortByBanPhatHanhDesc() trả về void sắp xếp theo bản phát hành giảm dần (chaining comparator # stream)
    public void sortByBanPhatHanhDesc() {
         repo.getAll().sort(Comparator.comparingInt(TaiLieu::getSoBanPhatHanh).reversed());
    }

    //8. thongKe() trả về void, in ra số lượng tài liệu của mỗi loại (Tổng tài liệu, sách, tạp chí, báo, Tài liệu phát hành nhiều nhất)
    public void thongKe() {
        long countSach = repo.getAll().stream().filter(s -> s instanceof Sach).count();
        long countTapChi = repo.getAll().stream().filter(s -> s instanceof TapChi).count();
        long countBao = repo.getAll().stream().filter(s -> s instanceof Bao).count();
        Optional<TaiLieu> maxDoc = repo.getAll().stream().max(Comparator.comparing(TaiLieu::getSoBanPhatHanh));
        System.out.println("Tong so tai lieu: " + repo.getAll().size());
        System.out.println("So luong sach: " + countSach);
        System.out.println("So luong tap chi: " + countTapChi);
        System.out.println("So luong bao: " + countBao);
        maxDoc.ifPresent(s -> System.out.println("tên tài liệu phát hành nhiều nhất: " + maxDoc.get().getTenTaiLieu()));
    }

    // save() / load() chỉ gọi repo.saveToFile() / repo.loadFromFile() vì liên quan đến dữ liệu
    public void save() throws IOException {
        repo.saveToFile();
    }

    public void load() throws IOException, ClassNotFoundException {
        repo.loadFromFile();
    }

}