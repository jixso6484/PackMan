
package com.packman.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.packman.Entity.UserEntity;


public interface userRepository extends JpaRepository<UserEntity,Long> {

    Optional <UserEntity> findByEmail(String email);
    public UserEntity save(UserEntity user);

}