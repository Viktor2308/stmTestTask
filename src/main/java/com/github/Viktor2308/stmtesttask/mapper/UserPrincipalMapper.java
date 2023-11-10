package com.github.Viktor2308.stmtesttask.mapper;

import com.github.Viktor2308.stmtesttask.entity.Role;
import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.security.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserPrincipalMapper {

    @Mapping(target = "authorities", source = "role", qualifiedByName = "roleMapper")
    UserPrincipal toUserPrincipal(User user);

    @Named("roleMapper")
    default Collection<? extends GrantedAuthority> roleMapper(Role role) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        roles.add(role);
        return roles;
    }
}
