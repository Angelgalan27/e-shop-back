package es.eshop.app.serviceImplTest;

import es.eshop.app.BaseConfigTests;
import es.eshop.app.entity.User;
import es.eshop.app.exception.BadRequestException;
import es.eshop.app.exception.ForbbidenException;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.IUserMapper;
import es.eshop.app.model.UserDTO;
import es.eshop.app.repository.IUserRepository;
import es.eshop.app.service.IUserService;
import es.eshop.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class UserServiceImplTest extends BaseConfigTests {

    private final Long ID = 1L;

    private final String NAME = "name";

    private final String SURNAME = "surname";

    private final String EMAIL = "email";

    private final String PASS = "123456";

    @InjectMocks
    private IUserService userService = new UserServiceImpl(null, null, null);

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder);
    }

    @Test
    void getAllTest(){
        Mockito.when(userRepository.findAll()).thenReturn(getListUser());
        Mockito.when(userMapper.toListModel(Mockito.anyList())).thenReturn(getListUserDto());
        List<UserDTO> list = userService.getAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void getByIdTest(){
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(getUserOptional());
        Mockito.when(userMapper.toModel(Mockito.any(User.class))).thenReturn(getUserDto());
        assertTrue(Objects.nonNull(userService.getById(ID)));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try {
            userService.getById(ID);
        } catch (NotFoundException e) {
            assertEquals("El usuario con el id "+ID+" no existe.", e.getMessage());
        }
    }

    @Test
    void saveTest() {
        UserDTO userDto = getUserDto();
        userDto.setId(null);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUser());
        Mockito.when(userMapper.toModel(Mockito.any(User.class))).thenReturn(getUserDto());
        Mockito.when(userMapper.toEntity(Mockito.any(UserDTO.class))).thenReturn(getUser());
        UserDTO userNew = userService.save(userDto);
        assertTrue(Objects.nonNull(userNew));
        assertEquals(ID, userNew.getId());
        userDto.setPassword(null);
        try {
            userService.save(userDto);
        } catch (BadRequestException e) {
            assertEquals("El campo contraseña es obligatorio.", e.getMessage());
        }

        userDto = getUserDto();
        userDto.setName(null);
        try {
            userService.save(userDto);
        } catch (BadRequestException e) {
            assertEquals("El campo nombre es obligatorio.", e.getMessage());
        }
        userDto = getUserDto();
        userDto.setEmail(null);
        try {
            userService.save(userDto);
        } catch (BadRequestException e) {
            assertEquals("El campo email es obligatorio.", e.getMessage());
        }
    }

    @Test
    void updateTest() {
        UserDTO userDto = getUserDto();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUser());
        Mockito.when(userMapper.toModel(Mockito.any(User.class))).thenReturn(getUserDto());
        Mockito.when(userMapper.toEntity(Mockito.any(UserDTO.class))).thenReturn(getUser());
        UserDTO userUpadate = userService.update(userDto);
        assertTrue(Objects.nonNull(userUpadate));
        assertEquals(ID, userUpadate.getId());
    }

    @Test
    void deleteByIdTest() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(getUserOptional());
        assertTrue(userService.deleteById(ID));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try {
            assertTrue(userService.deleteById(ID));
        } catch (NotFoundException e) {
            assertEquals("El usuario con el id "+ID+" no existe.", e.getMessage());
        }
    }

    @Test
    void getByEmailTest() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserOptional());
        Mockito.when(userMapper.toModel(Mockito.any(User.class))).thenReturn(getUserDto());
        assertTrue(Objects.nonNull(userService.getByEmail(EMAIL)));
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        try {
            userService.getByEmail(EMAIL);
        } catch (NotFoundException e) {
            assertEquals("El usuario con el email "+EMAIL+" no existe.", e.getMessage());
        }

    }

    @Test
    void authenticate(){
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getUserOptional());
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(Boolean.TRUE);
        assertTrue(Objects.nonNull(userService.authenticate(new UsernamePasswordAuthenticationToken(EMAIL, PASS))));
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(Boolean.FALSE);

        try {
            userService.authenticate(new UsernamePasswordAuthenticationToken(EMAIL, PASS));
        } catch (ForbbidenException e){
            assertEquals("Error al autenticar el usuario.", e.getMessage());
        }
    }

    private List<User> getListUser() {
        return Collections.singletonList(getUser());
    }

    private User getUser() {
        User user = new User();
        user.setId(ID);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setPassword(PASS);
        user.setEmail(EMAIL);
        return user;
    }

    private Optional<User> getUserOptional() {
        return Optional.of(getUser());
    }

    private List<UserDTO> getListUserDto() {
        return Collections.singletonList(getUserDto());
    }

    private UserDTO getUserDto() {
        return UserDTO.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .email(EMAIL)
                .password(PASS)
                .build();
    }
}
