package com.photoz.clone.service;

import com.photoz.clone.api.dto.UserCreateEditDto;
import com.photoz.clone.api.dto.UserReadDto;
import com.photoz.clone.api.mapper.UserCreateEditMapper;
import com.photoz.clone.api.mapper.UserReadMapper;
import com.photoz.clone.config.SecurityConfiguration;
import com.photoz.clone.store.entity.Role;
import com.photoz.clone.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService, OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserRepository userRepository;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserReadMapper userReadMapper;

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new User(username, user.getPassword(), Collections.singleton(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));

    }

    @Transactional
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        String username = userRequest.getIdToken().getClaim("email");
        UserDetails userDetails = getUserDetails(userRequest, username);

        DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

        Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());

        return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                new Class[]{UserDetails.class, OidcUser.class},
                (proxy, method, args) -> userDetailsMethods.contains(method)
                        ? method.invoke(userDetails, args)
                        : method.invoke(oidcUser, args));
    }

    private UserDetails getUserDetails(OidcUserRequest userRequest, String username) {
        return userRepository.findByUsername(username)
                .map(user -> new User(username, user.getPassword(), Collections.singleton(user.getRole())))
                .orElseGet(() -> {
                    String firstname = userRequest.getIdToken().getClaim("given_name");
                    String lastname = userRequest.getIdToken().getClaim("family_name");
                    String password = UUID.randomUUID().toString();
                    Role role = Role.USER;
                    UserCreateEditDto userDto = new UserCreateEditDto(username, password, firstname, lastname, role);
                    return Optional.of(userDto)
                            .map(userCreateEditMapper::map)
                            .map(userRepository::save)
                            .map(u -> new User(u.getUsername(), u.getPassword(), Collections.singleton(u.getRole())))
                            .orElseThrow();
                });
    }
}
