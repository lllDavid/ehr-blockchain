package com.ehrblockchain.user.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ehrblockchain.user.model.User;
import com.ehrblockchain.user.dto.UserCreateDTO;
import com.ehrblockchain.user.dto.UserDTO;
import com.ehrblockchain.user.dto.UserUpdateDTO;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    User toEntity(UserCreateDTO dto);

    UserDTO toDto(User user);

    void updateFromDto(UserUpdateDTO dto, @MappingTarget User user);

    @AfterMapping
    default void linkPatient(UserUpdateDTO dto, @MappingTarget User user) {
        if (dto.getPatientId() != null) {
        }
    }
}