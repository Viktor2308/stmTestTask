package com.github.Viktor2308.stmtesttask.mapper;

import com.github.Viktor2308.stmtesttask.dto.CreateUserDto;
import com.github.Viktor2308.stmtesttask.entity.Role;
import com.github.Viktor2308.stmtesttask.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.github.Viktor2308.stmtesttask.entity.Role.*;


@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    User toUser(CreateUserDto createUserDto);

}
