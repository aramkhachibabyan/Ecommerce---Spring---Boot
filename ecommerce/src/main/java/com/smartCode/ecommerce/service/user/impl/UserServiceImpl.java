package com.smartCode.ecommerce.service.user.impl;

import com.smartCode.ecommerce.exceptions.UserNotFoundException;
import com.smartCode.ecommerce.exceptions.ValidationException;
import com.smartCode.ecommerce.exceptions.VerificationException;
import com.smartCode.ecommerce.mapper.UserMapper;
import com.smartCode.ecommerce.model.dto.user.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UpdateUserDto;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.repository.user.UserRepository;
import com.smartCode.ecommerce.service.email.EmailService;
import com.smartCode.ecommerce.service.user.UserService;
import com.smartCode.ecommerce.util.RandomGenerator;
import com.smartCode.ecommerce.util.constants.Message;
import com.smartCode.ecommerce.util.encoder.MD5Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Override
    @Transactional
    public ResponseUserDto register(CreateUserDto user) {
        UserEntity entity = userMapper.toEntity(user);
        entity.setPassword(MD5Encoder.encode(entity.getPassword()));
        entity.setCode(RandomGenerator.generateNumericString(6));
        UserEntity save = userRepository.save(entity);
        emailService.sendSimpleMessage(entity.getEmail(), "Verification", "Your verification code is " + save.getCode());
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
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDto getById(Integer id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public ResponseUserDto changePassword(String email, String newPassword, String repeatPassword) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND);
        }
        if (!Objects.equals(newPassword, repeatPassword)) {
            throw new ValidationException(Message.PASSWORD_MATCHING);
        }
        user.setPassword(MD5Encoder.encode(newPassword));
        UserEntity save = userRepository.save(user);
        return userMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseUserDto verify(String email, String code) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND);
        }
        if (!user.getCode().equals(code)) {
            throw new ValidationException(Message.INVALID_CODE);
        }
        user.setIsVerified(true);
        userRepository.save(user);
        return userMapper.toDto(user);
    }


    @Override
    @Transactional
    public ResponseUserDto delete(Integer id, String password) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND));
        if (!Objects.equals(user.getPassword(), MD5Encoder.encode(password))) {
            throw new ValidationException(Message.INVALID_PASSWORD);
        }
        userRepository.delete(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public ResponseUserDto updatePartially(Integer id, PartialUpdateUserDto updatedUser) {
        UserEntity user1 = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND));

        user1.setEmail(nonNull(updatedUser.getEmail()) ? updatedUser.getEmail() : user1.getEmail());
        user1.setName(nonNull(updatedUser.getName()) ? updatedUser.getName() : user1.getName());
        user1.setLastname(nonNull(updatedUser.getLastname()) ? updatedUser.getLastname() : user1.getLastname());
        user1.setMiddleName(nonNull(updatedUser.getMiddleName()) ? updatedUser.getMiddleName() : user1.getMiddleName());
        user1.setUsername(nonNull(updatedUser.getUsername()) ? updatedUser.getUsername() : user1.getUsername());
        user1.setPhone(nonNull(updatedUser.getPhone()) ? updatedUser.getPhone() : user1.getPhone());
        user1.setGender(nonNull(updatedUser.getGender()) ? updatedUser.getGender() : user1.getGender());
        user1.setDayOfBirth(nonNull(updatedUser.getDayOfBirth()) ? updatedUser.getDayOfBirth() : user1.getDayOfBirth());
        UserEntity save = userRepository.save(user1);
        return userMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseUserDto updateUser(Integer id, UpdateUserDto updatedUser) {
        UserEntity user1 = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND));
        UserEntity entity = userMapper.toEntity(updatedUser, user1);
        UserEntity save = userRepository.save(entity);
        return userMapper.toDto(save);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDto login(String email,
                                 String password) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(Message.USER_NOT_FOUND);
        }
        if (!user.getIsVerified()) {
            throw new VerificationException(Message.VERIFY_FIRST);
        }
        if (!Objects.equals(user.getPassword(), MD5Encoder.encode(password))) {
            throw new ValidationException(Message.INVALID_PASSWORD);
        }
        return userMapper.toDto(user);
    }

}
