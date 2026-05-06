package com.costumestore.userservice.repository;

import com.costumestore.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findByIds(@Param("ids") List<Long> ids);

    Optional<User> findByUsername(String username);
}
