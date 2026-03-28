package com.ou.springdemo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ou.springdemo.entity.Role;
import com.ou.springdemo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    //Tự động có những method CRUD

    // Query derivation: Phát sinh truy vấn từ tên method
    // Dành cho method không có annotation @Query
   
    // vd: findByUsername
    // Cơ chế: Spring Data JPA đọc tên method, suy ra điều kiện truy vấn -> Tự sinh ra JPQL
    
    // Quy ước tên:
    // findBy... -> SELECT, điều kiện theo thuộc tính sau "By"
    // exitsBy... -> Kiểm tra tồn tại (COUNT / EXISTS)
    // ...AndIdNot -> Thêm điều kiện "AND id != :id" (Là: để tránh trùng lặp khi update)

    // Không viết câu lệnh truy vấn, chỉ cần đặt tên method đúng quy tắc => Spring Data JPA sẽ implement giúp
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Tìm kiếm theo search (username hoặc email chứa chuỗi input) và role)
    // Trả về một trang kết quả (Page) chứa các đối tượng User
    // Ứng với GET /api/users?search=abc&role=ADMIN&page=0&size=10
    @Query("SELECT u FROM User u WHERE " + 
        "(:search IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))) " +
        "AND (:role IS NULL OR u.role = :role)"
    )
    //Giải nghĩa: Nếu tham số :search là NULL, bỏ qua điều kiện tìm kiếm (tức là trả về tất cả người dùng).
    //Nếu :search không NULL, so sánh chuỗi tìm kiếm (đã chuyển thành chữ thường) với tên người dùng (cũng đã chuyển thành chữ thường) để tìm kiếm người dùng có tên chứa chuỗi tìm kiếm đó.
    Page<User> findAllBySearchAndRole(@Param("search") String search, @Param("role") Role role, Pageable pageable);
}
