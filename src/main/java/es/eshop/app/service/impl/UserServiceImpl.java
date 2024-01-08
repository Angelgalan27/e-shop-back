package es.eshop.app.service.impl;

import es.eshop.app.entity.User;
import es.eshop.app.exception.BadRequestException;
import es.eshop.app.exception.ForbbidenException;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.IUserMapper;
import es.eshop.app.model.UserDTO;
import es.eshop.app.repository.IUserRepository;
import es.eshop.app.service.IUserService;
import es.eshop.app.util.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final IUserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;
    public UserServiceImpl(IUserRepository userRepository,
                           IUserMapper userMapper,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * @return list
     */
    @Override
    public List<UserDTO> getAll() {
        return userMapper.toListModel(userRepository.findAll());
    }

    /**
     *
     * @param aLong
     * @return
     */
    @Override
    public UserDTO getById(Long aLong) {
        return userMapper.toModel(userRepository.findById(aLong)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage("user.not.found", aLong))));
    }

    /**
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserDTO save(UserDTO userDTO) {
        if (Objects.isNull(userDTO.getPassword())) {
            throw new BadRequestException(Resource.getMessage("user.password.mandatory"));
        }

        if (Objects.isNull(userDTO.getName())) {
            throw new BadRequestException(Resource.getMessage("user.name.mandatory"));
        }

        if (Objects.isNull(userDTO.getEmail())) {
            throw new BadRequestException(Resource.getMessage("user.email.mandatory"));
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userMapper.toModel(
                userRepository.save(userMapper.toEntity(userDTO))
        );
    }

    /**
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserDTO update(UserDTO userDTO) {
        return userMapper.toModel(
                userRepository.save(userMapper.toEntity(userDTO))
        );
    }

    /**
     *
     * @param aLong
     * @return
     */
    @Override
    public Boolean deleteById(Long aLong) {
        User user = userRepository.findById(aLong)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage("user.not.found", aLong)));
        userRepository.delete(user);
        return Boolean.TRUE;
    }

    /**
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage("user.not.found.email", email)));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Collections.emptyList());
    }

    /**
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = loadUserByUsername((String) authentication.getPrincipal());
        if(Boolean.FALSE.equals(passwordEncoder.matches((String) authentication.getCredentials(), userDetails.getPassword()))) {
            log.error("error authenticate");
            throw new ForbbidenException(Resource.getMessage("user.authenticate.error"));
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    /**
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    /**
     *
     * @param email
     * @return
     */
    @Override
    public UserDTO getByEmail(String email) {
        return userMapper.toModel(userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage("user.not.found.email", email))));
    }
}
