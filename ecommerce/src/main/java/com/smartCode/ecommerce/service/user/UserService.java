package com.smartCode.ecommerce.service.user;

import com.smartCode.ecommerce.model.dto.user.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UpdateUserDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    ResponseUserDto register(CreateUserDto user);
    List<ResponseUserDto> getAllUsers();

    ResponseUserDto getById(Integer id);

    ResponseUserDto verify(Integer id, String code);

    ResponseUserDto updatePartially(Integer id, PartialUpdateUserDto updatedUser);

    ResponseUserDto login(String email, String password);

    ResponseUserDto delete(Integer id,String password);

    ResponseUserDto updateUser(Integer id,UpdateUserDto updatedUser);
    ResponseUserDto changePassword(Integer id,String oldPassword, String newPassword, String repeatPassword);
}
