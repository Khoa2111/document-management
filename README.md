# 📚 Phần Mềm Quản Lý Thư Viện

Ứng dụng console Java 21 để quản lý danh sách tài liệu thư viện (Sách, Tạp chí, Báo).

## Kiến trúc (sau khi refactor)

```
ThuvienApp (entry point)
    └── QuanLyThuVien  (UI layer)
            └── ThuVienService  (service layer)
                    └── TaiLieuRepository  (interface)
                            └── BinaryTaiLieuRepository  (implementation)
```

### Các tầng

| Tầng | Class | Trách nhiệm |
|------|-------|-------------|
| **UI** | `QuanLyThuVien` | Menu, nhập liệu, hiển thị. Không chứa logic nghiệp vụ. |
| **Service** | `ThuVienService` | CRUD, tìm kiếm, sắp xếp, thống kê, dirty flag. |
| **Repository** | `BinaryTaiLieuRepository` | Lưu/đọc `.dat` (Serialization), xuất `.txt` (NIO). |
| **Domain** | `TaiLieu`, `Sach`, `TapChi`, `Bao` | Model với validation. |
| **Exceptions** | `MaTaiLieuTrungException`, `TaiLieuNotFoundException` | Lỗi nghiệp vụ. |

## Tính năng

1. ➕ Thêm tài liệu (Sách / Tạp chí / Báo)
2. 📖 Hiển thị danh sách
3. ❌ Xóa tài liệu
4. ✏️ Sửa tài liệu
5. 🔍 Tìm kiếm theo loại
6. 🔎 Tìm kiếm theo tên / NXB
7. 📊 Sắp xếp theo số bản phát hành (giảm dần)
8. 📈 Thống kê tổng hợp
9. 💾 Ghi file nhị phân (`data/thuvien.dat`)
10. 📂 Đọc file nhị phân (an toàn với dirty flag)
11. 📝 Xuất file văn bản (`data/thuvien.txt`)

## UX an toàn dữ liệu

- **Auto-load**: Khi khởi động, tự động đọc `data/thuvien.dat` nếu tồn tại.
- **Dirty flag**: Menu hiện `[CHƯA LƯU]` khi có thay đổi chưa được lưu.
- **Safe reload**: Khi bấm "Đọc file" mà có dữ liệu chưa lưu, chương trình hỏi:
  - (1) Lưu rồi đọc lại
  - (2) Bỏ thay đổi và đọc lại
  - (0) Hủy

## Chạy chương trình

```bash
javac -encoding UTF-8 src/model/*.java src/exception/*.java src/repository/*.java src/service/*.java src/app/*.java
java ThuvienApp
```

## Cấu trúc file

```
├── src/
│   ├── app/
│   │   ├── ThuvienApp.java              # Entry point
│   │   └── QuanLyThuVien.java           # UI layer
│   ├── service/
│   │   └── ThuVienService.java          # Service layer
│   ├── repository/
│   │   ├── TaiLieuRepository.java       # Repository interface
│   │   └── BinaryTaiLieuRepository.java # Repository implementation
│   ├── model/
│   │   ├── TaiLieu.java                 # Abstract domain model
│   │   ├── Sach.java                    # Concrete: Sách
│   │   ├── TapChi.java                  # Concrete: Tạp chí
│   │   └── Bao.java                     # Concrete: Báo
│   └── exception/
│       ├── MaTaiLieuTrungException.java # Domain exception
│       └── TaiLieuNotFoundException.java# Domain exception
└── data/                                # Runtime data directory (auto-created)
    ├── thuvien.dat                      # Binary data file
    └── thuvien.txt                      # Exported text file
```
