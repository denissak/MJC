package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends RepresentationModel<UserDto> implements UserDetails {
    private Long id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;
    private RoleDto roleDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(roleDto);
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
