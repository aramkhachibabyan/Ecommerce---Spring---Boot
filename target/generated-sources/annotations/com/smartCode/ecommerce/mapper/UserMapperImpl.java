package com.smartCode.ecommerce.mapper;

import com.smartCode.ecommerce.model.dto.user.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UpdateUserDto;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-29T02:44:03+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(CreateUserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setName( userDto.getName() );
        userEntity.setLastname( userDto.getLastname() );
        userEntity.setMiddleName( userDto.getMiddleName() );
        userEntity.setUsername( userDto.getUsername() );
        userEntity.setEmail( userDto.getEmail() );
        userEntity.setPhone( userDto.getPhone() );
        userEntity.setPassword( userDto.getPassword() );
        userEntity.setGender( userDto.getGender() );
        userEntity.setDayOfBirth( userDto.getDayOfBirth() );

        return userEntity;
    }

    @Override
    public ResponseUserDto toDto(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserDto responseUserDto = new ResponseUserDto();

        responseUserDto.setName( user.getName() );
        responseUserDto.setLastname( user.getLastname() );
        responseUserDto.setMiddleName( user.getMiddleName() );
        responseUserDto.setUsername( user.getUsername() );
        responseUserDto.setEmail( user.getEmail() );
        responseUserDto.setPhone( user.getPhone() );
        responseUserDto.setGender( user.getGender() );
        responseUserDto.setDayOfBirth( user.getDayOfBirth() );
        responseUserDto.setCreatedAt( user.getCreatedAt() );
        responseUserDto.setIsVerified( user.getIsVerified() );
        responseUserDto.setAge( user.getAge() );

        return responseUserDto;
    }

    @Override
    public UserEntity toEntity(UpdateUserDto updateUserDto, UserEntity userEntity) {
        if ( updateUserDto == null ) {
            return userEntity;
        }

        userEntity.setName( updateUserDto.getName() );
        userEntity.setLastname( updateUserDto.getLastname() );
        userEntity.setMiddleName( updateUserDto.getMiddleName() );
        userEntity.setUsername( updateUserDto.getUsername() );
        userEntity.setEmail( updateUserDto.getEmail() );
        userEntity.setPhone( updateUserDto.getPhone() );
        userEntity.setGender( updateUserDto.getGender() );
        userEntity.setDayOfBirth( updateUserDto.getDayOfBirth() );

        return userEntity;
    }
}
