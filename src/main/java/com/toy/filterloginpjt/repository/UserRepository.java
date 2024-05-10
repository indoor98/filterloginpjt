package com.toy.filterloginpjt.repository;

import com.toy.filterloginpjt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);


    List<User> findByEmail(String email);
}
