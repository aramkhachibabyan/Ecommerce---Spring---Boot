package com.smartCode.ecommerce.repository.user;

import com.smartCode.ecommerce.model.entity.user.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    UserEntity findByPhone(String phone);
    UserEntity findByPhoneOrEmailOrUsername(String phone, String email, String username);

    List<UserEntity> findAll(Specification specification);

    boolean existsByUsername(String admin);
}
