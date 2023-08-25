package com.smartCode.ecommerce.service.user;

import com.smartCode.ecommerce.model.dto.user.FilterSearchUser;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.auth.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.update.ChangePasswordUserDto;
import com.smartCode.ecommerce.model.dto.user.update.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.update.UpdateBaseUserDto;
import com.smartCode.ecommerce.model.dto.user.auth.UserAuthDto;
import com.smartCode.ecommerce.model.dto.user.auth.UserLoginDto;
import com.smartCode.ecommerce.model.dto.user.auth.VerificationDto;

import java.util.List;

public interface UserService {
    ResponseUserDto register(CreateUserDto user);
    List<ResponseUserDto> getAllUsers();

    ResponseUserDto getById(Integer id);

    ResponseUserDto verify(VerificationDto dto);

    ResponseUserDto updatePartially(Integer id, PartialUpdateUserDto updatedUser);

    UserAuthDto login(UserLoginDto dto);

    ResponseUserDto delete(Integer id);

    ResponseUserDto updateUser(Integer id, UpdateBaseUserDto updatedUser);
    ResponseUserDto changePassword(Integer id, ChangePasswordUserDto dto);


    void logout(String token);

    List<ResponseUserDto> search(FilterSearchUser.Search userSearch);

    List<ResponseUserDto> filter(FilterSearchUser.Filter userFilter);
}
