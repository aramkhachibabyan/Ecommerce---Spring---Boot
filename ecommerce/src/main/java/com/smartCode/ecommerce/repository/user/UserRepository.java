package com.smartCode.ecommerce.repository.user;

import com.smartCode.ecommerce.model.entity.user.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    UserEntity findByPhone(String phone);
    UserEntity findByPhoneOrEmailOrUsername(String phone, String email, String username);

    void findAll(Specification specification);

    boolean existsByUsername(String admin);
}
