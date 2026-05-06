# Costume Store - Microservices System

Hệ thống quản lý nhập trang phục và thống kê doanh thu theo dòng trang phục.

## Kiến trúc tổng thể

```
costume-store/
├── user-service              (port 8081) → DB: user_db
├── costume-service           (port 8082) → DB: costume_db
├── supplier-service          (port 8083) → DB: supplier_db
├── importing-bill-service    (port 8084) → DB: importing_bill_db
├── bill-costume-service      (port 8085) → DB: bill_db
└── web-client                (port 8090) → Thymeleaf MVC
```

## Yêu cầu hệ thống

- Java 17
- Gradle (dùng wrapper `gradlew` / `gradlew.bat` trong repo)
- MySQL 8.x
- (Tuỳ chọn) Docker & Docker Compose

## Cài đặt Database

Tạo các database MySQL sau:

```sql
CREATE DATABASE user_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE costume_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supplier_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE importing_bill_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE bill_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Cấu hình mặc định: MySQL chạy tại `localhost:3306`, user `root`, password `root`.
Chỉnh sửa trong `src/main/resources/application.yml` của từng service nếu cần.

## Chạy các service

### Cách 1: Chạy từng service riêng (từ thư mục gốc repo)

Trên Windows dùng `gradlew.bat` thay cho `gradlew`.

```bash
# Thứ tự khởi động khuyến nghị
./gradlew :user-service:bootRun
./gradlew :costume-service:bootRun
./gradlew :supplier-service:bootRun
./gradlew :importing-bill-service:bootRun
./gradlew :bill-costume-service:bootRun
./gradlew :web-client:bootRun
```

### Cách 2: Build tất cả từ root

```bash
./gradlew clean build -x test
```

## Truy cập ứng dụng

- **Web Client (UI):** http://localhost:8090
- **User Service API:** http://localhost:8081/api/users
- **Costume Service API:** http://localhost:8082/api/costumes
- **Supplier Service API:** http://localhost:8083/api/suppliers
- **Importing Bill Service API:** http://localhost:8084/api/importing-bills
- **Bill Costume Service API:** http://localhost:8085/api/bills

## Mô-đun chức năng

### 1. Nhập trang phục từ nhà cung cấp
- URL: http://localhost:8090/importing-costume
- Luồng: Chọn trang phục → Chọn nhà cung cấp → Xác nhận nhập → Thành công

### 2. Thống kê doanh thu theo dòng trang phục
- URL: http://localhost:8090/statistics/costume-line-form
- Luồng: Nhập khoảng thời gian → Xem doanh thu theo dòng → Chi tiết trang phục → Danh sách hoá đơn

## Công nghệ sử dụng

| Thành phần       | Công nghệ                          |
|------------------|------------------------------------|
| Framework        | Spring Boot 4.x (theo `build.gradle` root) |
| ORM              | Spring Data JPA + Hibernate        |
| Database         | MySQL 8                            |
| Service Comm.    | OpenFeign (Spring Cloud 2023.0.1)  |
| View Layer       | Thymeleaf + Bootstrap 5            |
| Validation       | Jakarta Validation                 |
| Boilerplate      | Lombok                             |
| Build Tool       | Gradle                             |

## Sample Data

Mỗi service có file `data.sql` tại `src/main/resources/` để seed dữ liệu mẫu.
Spring Boot sẽ tự động chạy file này khi khởi động (cấu hình `spring.sql.init.mode=always`).
# NMH
