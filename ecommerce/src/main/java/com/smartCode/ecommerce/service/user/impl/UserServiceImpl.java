package com.smartCode.ecommerce.service.user.impl;

import com.smartCode.ecommerce.exceptions.DuplicationException;
import com.smartCode.ecommerce.exceptions.ResourceNotFoundException;
import com.smartCode.ecommerce.exceptions.ValidationException;
import com.smartCode.ecommerce.mapper.UserMapper;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UserDetailsImpl;
import com.smartCode.ecommerce.model.dto.user.auth.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.auth.UserAuthDto;
import com.smartCode.ecommerce.model.dto.user.auth.UserLoginDto;
import com.smartCode.ecommerce.model.dto.user.auth.VerificationDto;
import com.smartCode.ecommerce.model.dto.user.update.ChangePasswordUserDto;
import com.smartCode.ecommerce.model.dto.user.update.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.update.UpdateBaseUserDto;
import com.smartCode.ecommerce.model.entity.token.TokenEntity;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.repository.role.RoleRepository;
import com.smartCode.ecommerce.repository.user.UserRepository;
import com.smartCode.ecommerce.service.action.impl.ActionServiceImpl;
import com.smartCode.ecommerce.service.card.CardService;
import com.smartCode.ecommerce.service.notification.NotificationService;
import com.smartCode.ecommerce.service.token.TokenService;
import com.smartCode.ecommerce.service.user.UserService;
import com.smartCode.ecommerce.spec.user.UserSpecification;
import com.smartCode.ecommerce.util.RandomGenerator;
import com.smartCode.ecommerce.util.constants.Actions;
import com.smartCode.ecommerce.util.constants.Message;
import com.smartCode.ecommerce.util.constants.Role;
import com.smartCode.ecommerce.util.constants.entityTypes;
import com.smartCode.ecommerce.util.event.producer.VerificationEventPublisher;
import com.smartCode.ecommerce.util.security.CurrentUser;
import com.smartCode.ecommerce.util.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CardService cardService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final NotificationService notificationService;
    private final VerificationEventPublisher verificationEventPublisher;
    private final ActionServiceImpl actionService;
    private final UserSpecification extractor;

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
        actionService.create(save.getId(), Actions.CREATE, entityTypes.USER);
        verificationEventPublisher.publishVerificationEvent(save);
//        notificationService.verify(entity.getEmail(), entity.getCode());
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
        actionService.create(save.getId(), Actions.UPDATE, entityTypes.USER);
        return userMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseUserDto verify(VerificationDto dto) {
        UserEntity user = userRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(dto.getId())));
        if (!user.getCode().equals(dto.getCode())) {
            throw new ValidationException(Message.INVALID_CODE);
        }
        user.setIsVerified(true);
        userRepository.save(user);
        actionService.create(user.getId(), Actions.VERIFY, entityTypes.USER);
        return userMapper.toDto(user);
    }


    @Override
    @Transactional
    public ResponseUserDto delete(Integer id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));
        cardService.deleteCardsByUserId(id);
        userRepository.delete(user);
        actionService.create(id, Actions.DELETE, entityTypes.USER);
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
        actionService.create(save.getId(), Actions.UPDATE, entityTypes.USER);
        return userMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseUserDto updateUser(Integer id, UpdateBaseUserDto updatedUser) {
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
        actionService.create(save.getId(), Actions.UPDATE, entityTypes.USER);

        return userMapper.toDto(save);
    }

    @Override
    @Transactional
    public UserAuthDto login(UserLoginDto dto) {
        String username = dto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, dto.getPassword()));
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenProvider.generateAccessToken(userDetails.getId(), userDetails.getUsername(),
                userDetails.getAuthority());
        TokenEntity tokenEntity = new TokenEntity();
        String token1 = token.split("\\.")[2];
        tokenEntity.setToken(token1);
        tokenEntity.setUser(userRepository.findByPhoneOrEmailOrUsername(username, username, username));
        actionService.create(userDetails.getId(), Actions.LOGIN, entityTypes.USER);
        tokenService.saveToken(tokenEntity);
        return new UserAuthDto(userDetails.getId(), token);
    }

    @Override
    @Transactional
    public void logout(String token) {
        Integer id = CurrentUser.getId();
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Message.userNotFound(id)));
        tokenService.deleteByUserAndToken(userEntity, token.split("\\.")[2]);
    }

    @Override
    @Transactional
    public List<ResponseUserDto> search(com.smartCode.ecommerce.model.dto.user.FilterSearchUser.Search userSearch) {
        return userRepository.findAll(extractor.search(userSearch)).stream().map(userMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<ResponseUserDto> filter(com.smartCode.ecommerce.model.dto.user.FilterSearchUser.Filter userFilter) {
        return userRepository.findAll(extractor.filter(userFilter)).stream().map(userMapper::toDto).toList();
    }


}
