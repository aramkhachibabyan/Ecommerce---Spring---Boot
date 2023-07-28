package com.smartCode.ecommerce.mapper;

import com.smartCode.ecommerce.model.dto.user.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UpdateUserDto;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(CreateUserDto userDto);

    ResponseUserDto toDto(UserEntity user);

    UserEntity toEntity(UpdateUserDto updateUserDto, @MappingTarget UserEntity userEntity);
}
