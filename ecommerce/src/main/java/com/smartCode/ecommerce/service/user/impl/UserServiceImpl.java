package com.smartCode.ecommerce.service.user.impl;

import com.smartCode.ecommerce.exceptions.DuplicationException;
import com.smartCode.ecommerce.exceptions.ResourceNotFoundException;
import com.smartCode.ecommerce.exceptions.ValidationException;
import com.smartCode.ecommerce.mapper.UserMapper;
import com.smartCode.ecommerce.model.dto.user.ChangePasswordUserDto;
import com.smartCode.ecommerce.model.dto.user.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.UserAuthDto;
import com.smartCode.ecommerce.model.dto.user.UserDetailsImpl;
import com.smartCode.ecommerce.model.entity.token.TokenEntity;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.repository.role.RoleRepository;
import com.smartCode.ecommerce.repository.user.UserRepository;
import com.smartCode.ecommerce.service.card.CardService;
import com.smartCode.ecommerce.service.email.EmailService;
import com.smartCode.ecommerce.service.token.TokenService;
import com.smartCode.ecommerce.service.user.UserService;
import com.smartCode.ecommerce.util.RandomGenerator;
import com.smartCode.ecommerce.util.constants.Message;
import com.smartCode.ecommerce.util.constants.Role;
import com.smartCode.ecommerce.util.security.CurrentUser;
import com.smartCode.ecommerce.util.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final CardService cardService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;

    @Override
    @Transactional
    public ResponseUserDto register(CreateUserDto user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new DuplicationException(Message.EMAIL_IS_NOT_AVAILABLE);
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new DuplicationException(Message.USERNAME_IS_NOT_AVAILABLE);
        }
        if (userRepository.findByPhone(user.getPhone()) != null) {
            throw new DuplicationException(Message.PHONE_NUMBER_IS_NOT_AVAILABLE);
        }
        UserEntity entity = userMapper.toEntity(user);
        entity.setRole(roleRepository.findByRole(Role.ROLE_USER));
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setCode(RandomGenerator.generateNumericString(6));
        entity.setAge(Year.now().getValue() - entity.getDayOfBirth().getYear());
        UserEntity save = userRepository.save(entity);
        emailService.sendSimpleMessage(entity.getEmail(), "Verification",
                "Your verification code is " + save.getCode());
        return userMapper.toDto(save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseUserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<ResponseUserDto> list = new ArrayList<>();
        for (UserEntity user : users) {
            list.add(userMapper.toDto(user));
        }
        for (ResponseUserDto responseUserDto : list) {
            responseUserDto.setCards(cardService.getCardsByUserId(responseUserDto.getId()));
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDto getById(Integer id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));
        ResponseUserDto dto = userMapper.toDto(user);
        dto.setCards(cardService.getCardsByUserId(id));
        return dto;
    }

    @Override
    @Transactional
    public ResponseUserDto changePassword(Integer id, ChangePasswordUserDto dto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));
        if (!Objects.equals(user.getPassword(), dto.getOldPassword())) {
            throw new ValidationException(Message.INVALID_OLD_PASSWORD);
        }
        if (!Objects.equals(dto.getNewPassword(), dto.getRepeatPassword())) {
            throw new ValidationException(Message.PASSWORD_MATCHING);
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        UserEntity save = userRepository.save(user);
        return userMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseUserDto verify(Integer id, String code) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));
        if (!user.getCode().equals(code)) {
            throw new ValidationException(Message.INVALID_CODE);
        }
        user.setIsVerified(true);
        userRepository.save(user);
        return userMapper.toDto(user);
    }


    @Override
    @Transactional
    public ResponseUserDto delete(Integer id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));
        cardService.deleteCardsByUserId(id);
        userRepository.delete(user);
        tokenService.deleteByUser(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public ResponseUserDto updatePartially(Integer id, PartialUpdateUserDto updatedUser) {
        UserEntity user1 = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));
        if (updatedUser.getEmail() != null) {
            if (userRepository.findByEmail(updatedUser.getEmail()) != null) {
                throw new DuplicationException(Message.EMAIL_IS_NOT_AVAILABLE);
            }
            user1.setIsVerified(false);
        }
        user1.setEmail(nonNull(updatedUser.getEmail()) ? updatedUser.getEmail() : user1.getEmail());
        user1.setName(nonNull(updatedUser.getName()) ? updatedUser.getName() : user1.getName());
        user1.setLastname(nonNull(updatedUser.getLastname()) ? updatedUser.getLastname() : user1.getLastname());
        user1.setMiddleName(nonNull(updatedUser.getMiddleName()) ? updatedUser.getMiddleName() : user1.getMiddleName());
        if (updatedUser.getUsername() != null && userRepository.findByUsername(updatedUser.getUsername()) != null) {
            throw new DuplicationException(Message.USERNAME_IS_NOT_AVAILABLE);
        }
        user1.setUsername(nonNull(updatedUser.getUsername()) ? updatedUser.getUsername() : user1.getUsername());
        if (updatedUser.getPhone() != null && userRepository.findByPhone(updatedUser.getPhone()) != null) {
            throw new DuplicationException(Message.PHONE_NUMBER_IS_NOT_AVAILABLE);
        }
        user1.setPhone(nonNull(updatedUser.getPhone()) ? updatedUser.getPhone() : user1.getPhone());
        user1.setGender(nonNull(updatedUser.getGender()) ? updatedUser.getGender() : user1.getGender());
        user1.setDayOfBirth(nonNull(updatedUser.getDayOfBirth()) ? updatedUser.getDayOfBirth() : user1.getDayOfBirth());
        user1.setAge(Year.now().getValue() - updatedUser.getDayOfBirth().getYear());
        UserEntity save = userRepository.save(user1);
        return userMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseUserDto updateUser(Integer id, UpdateUserDto updatedUser) {
        UserEntity user1 = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));

        if (userRepository.findByEmail(updatedUser.getEmail()) != null) {
            throw new DuplicationException(Message.EMAIL_IS_NOT_AVAILABLE);
        }
        user1.setIsVerified(false);

        if (userRepository.findByUsername(updatedUser.getUsername()) != null) {
            throw new DuplicationException(Message.USERNAME_IS_NOT_AVAILABLE);
        }
        user1.setUsername(nonNull(updatedUser.getUsername()) ? updatedUser.getUsername() : user1.getUsername());
        if (userRepository.findByPhone(updatedUser.getPhone()) != null) {
            throw new DuplicationException(Message.PHONE_NUMBER_IS_NOT_AVAILABLE);
        }
        UserEntity entity = userMapper.toEntity(updatedUser, user1);
        UserEntity save = userRepository.save(entity);
        return userMapper.toDto(save);
    }

    @Override
    @Transactional
    public UserAuthDto login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenProvider.generateAccessToken(userDetails.getId(), userDetails.getUsername(),
                userDetails.getAuthority());
        if (tokenService.findTokenEntityByUserId(userDetails.getId()) == null) {
            TokenEntity tokenEntity = new TokenEntity();
            String token1 = token.split("\\.")[2];
            tokenEntity.setToken(token1);
            tokenEntity.setUser(userRepository.findByPhoneOrEmailOrUsername(username, username, username));
            tokenService.saveToken(tokenEntity);
        }
        return new UserAuthDto(userDetails.getId(), token);
    }

    @Override
    @Transactional
    public void logout() {
        Integer id = CurrentUser.getId();
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));
        tokenService.deleteByUser(userEntity);
    }
    //    @Override
//    @Transactional
//    public List<ResponseUserDto> search(FilterSearchUser.Search userSearch) {
//        Specification<UserEntity> specification = Specification.where((root, criteriaQuery, criteriaBuilder) -> {
//            var predicates = new ArrayList<Predicate>();
//
//            if (nonNull(userSearch.getText())) {
//                Predicate nameLike = criteriaBuilder.like(root.get(Root.NAME), "%" + userSearch.getText() + "%");
//                predicates.add(nameLike);
//
//                Predicate lastNameLike = criteriaBuilder.like(root.get(Root.LASTNAME), "%" + userSearch.getText() + "%");
//                predicates.add(lastNameLike);
//
//                Predicate emailLike = criteriaBuilder.like(root.get(Root.EMAIL), "%" + userSearch.getText() + "%");
//                predicates.add(emailLike);
//            }
//            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
//        });
//        return userMapper.toDto(userRepository.findAll(specification));
//    }
//
//    @Override
//    @Transactional
//    public List<ResponseUserDto> filter(FilterSearchUser.Filter userFilter) {
//        Specification<UserEntity> specification = Specification.where((root, criteriaQuery, criteriaBuilder) -> {
//            var predicates = new ArrayList<Predicate>();
//
//            if (nonNull(userFilter.getIsVerified())) {
//                Predicate isVerified = criteriaBuilder.equal(root.get(Root.IS_VERIFIED), userFilter.getIsVerified());
//                predicates.add(isVerified);
//            }
//            if (nonNull(userFilter.getGender())) {
//                Predicate gender = criteriaBuilder.equal(root.get(Root.GENDER), userFilter.getGender());
//                predicates.add(gender);
//            }
//            if (nonNull(userFilter.getStartAge())) {
//                Predicate startAge = criteriaBuilder.greaterThan(root.get(Root.AGE), userFilter.getStartAge());
//                predicates.add(startAge);
//            }
//            if (nonNull(userFilter.getEndAge())) {
//                Predicate endAge = criteriaBuilder.lessThan(root.get(Root.AGE), userFilter.getEndAge());
//                predicates.add(endAge);
//            }
//            /*if (nonNull(userFilter.getProductName())) {
//                Join<UserEntity, ProductEntity> products = root.join("productEntities");
//                Predicate productName = criteriaBuilder.equal(products.get("name"), userFilter.getProductName());
//                predicates.add(productName);
//            }*/
//
//
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        });
//        return userMapper.toDto(userRepository.findAll(specification));
//    }


}
