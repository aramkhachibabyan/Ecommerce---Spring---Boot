//package com.smartCode.ecommerce.configuration;
//
//import com.smartCode.ecommerce.model.entity.role.RoleEntity;
//import com.smartCode.ecommerce.model.entity.user.UserEntity;
//import com.smartCode.ecommerce.repository.role.RoleRepository;
//import com.smartCode.ecommerce.repository.user.UserRepository;
//import com.smartCode.ecommerce.util.constants.Gender;
//import com.smartCode.ecommerce.util.constants.Role;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import java.time.LocalDate;
//
//@Configuration
//@RequiredArgsConstructor
//public class InitConfiguration {
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//
//    @PostConstruct
//    @Transactional
//    public void setupDB() {
//        roles();
//        createAdmin();
//    }
//
//    private void createAdmin() {
//            if (!userRepository.existsByUsername("admin")){
//                UserEntity admin = new UserEntity();
//                admin.setRole(roleRepository.findByRole(Role.ROLE_ADMIN));
//                admin.setAge(21);
//                admin.setIsVerified(true);
//                admin.setPhone("+37497171717");
//                admin.setUsername("admin");
//                admin.setPassword(passwordEncoder.encode("password"));
//                admin.setCode("123456");
//                admin.setDayOfBirth(LocalDate.parse("2002-05-03"));
//                admin.setGender(Gender.MALE);
//                admin.setName("Aram");
//                admin.setLastname("Khachibabyan");
//                admin.setEmail("aramkhachibabyan@gmail.com");
//                userRepository.save(admin);
//            }
//    }
//
//    private void roles() {
//        if (!roleRepository.existsByRole(Role.ROLE_ADMIN)) {
//            RoleEntity admin = new RoleEntity();
//            admin.setRole(Role.ROLE_ADMIN);
//            roleRepository.save(admin);
//        }
//        if (!roleRepository.existsByRole(Role.ROLE_USER)) {
//            RoleEntity user = new RoleEntity();
//            user.setRole(Role.ROLE_USER);
//            roleRepository.save(user);
//        }
//    }
//}
