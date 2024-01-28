package es.eshop.app.serviceImplTest;

import es.eshop.app.BaseConfigTests;
import es.eshop.app.entity.OpinionProduct;
import es.eshop.app.entity.Product;
import es.eshop.app.entity.User;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.IProductMapper;
import es.eshop.app.mapper.IUserMapper;
import es.eshop.app.mapper.OpinionProductMapper;
import es.eshop.app.model.OpinionProductDTO;
import es.eshop.app.repository.OpinionProductRepository;
import es.eshop.app.service.IOpinionProductService;
import es.eshop.app.service.IProductService;
import es.eshop.app.service.IUserService;
import es.eshop.app.service.impl.OpinionProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class OpinionProductServiceImplTest extends BaseConfigTests {

    private final Long ID = 1L;

    private final String COMMENT = "comment";

    private final Integer ASSESSMENT = 4;

    @InjectMocks
    private IOpinionProductService opinionProductService = new OpinionProductServiceImpl(null, null, null, null, null, null);

    @Mock
    private OpinionProductRepository opinionProductRepository;

    @Mock
    private IUserService userService;

    @Mock
    private IProductService productService;
    @Mock
    private OpinionProductMapper opinionProductMapper;

    @Mock
    private IUserMapper userMapper;

    @Mock
    private IProductMapper productMapper;

    @BeforeEach
    void init() {
        opinionProductService = new OpinionProductServiceImpl(opinionProductRepository, userService, productService, opinionProductMapper, userMapper, productMapper);
    }

    @Test
    void getAllByUserAndProductIdTest() {
        when(opinionProductRepository.findByUserAndProduct(Mockito.any(User.class), Mockito.any(Product.class))).thenReturn(Collections.singletonList(getOpinionProduct()));
        when(opinionProductMapper.toListModel(Mockito.anyList())).thenReturn(Collections.singletonList(getOpinionProductDto()));
        assertFalse(opinionProductService.getAllByUserAndProductId(ID, ID).isEmpty());
    }

    @Test
    void getAllByUserTest() {
        when(opinionProductRepository.findByUser(Mockito.any(User.class))).thenReturn(Collections.singletonList(getOpinionProduct()));
        when(opinionProductMapper.toListModel(Mockito.anyList())).thenReturn(Collections.singletonList(getOpinionProductDto()));
        assertFalse(opinionProductService.getAllByUser(ID).isEmpty());
    }

    @Test
    void getAllByProductIdTest() {
        when(opinionProductRepository.findByProduct(Mockito.any(Product.class))).thenReturn(Collections.singletonList(getOpinionProduct()));
        when(opinionProductMapper.toListModel(Mockito.anyList())).thenReturn(Collections.singletonList(getOpinionProductDto()));
        assertFalse(opinionProductService.getAllByProductId(ID).isEmpty());
    }

    @Test
    void getByIdTest() {
        when(opinionProductRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getOpinionProduct()));
        when(opinionProductMapper.toModel(Mockito.any(OpinionProduct.class))).thenReturn(getOpinionProductDto());
        assertTrue(Objects.nonNull(opinionProductService.getById(ID)));
        when(opinionProductRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try {
            opinionProductService.getAllByProductId(ID);
        } catch (NotFoundException e) {
            assertEquals("El comentario con el id "+ID+" no existe.", e.getMessage());
        }
    }

    @Test
    void saveTest() {
        when(opinionProductRepository.save(Mockito.any(OpinionProduct.class))).thenReturn(getOpinionProduct());
        when(opinionProductMapper.toModel(Mockito.any(OpinionProduct.class))).thenReturn(getOpinionProductDto());
        when(opinionProductMapper.toEntity(Mockito.any(OpinionProductDTO.class))).thenReturn(getOpinionProduct());
        assertTrue(Objects.nonNull(opinionProductService.save(getOpinionProductDto())));
    }

    @Test
    void updateTest() {
        when(opinionProductRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getOpinionProduct()));
        when(opinionProductRepository.save(Mockito.any(OpinionProduct.class))).thenReturn(getOpinionProduct());
        when(opinionProductMapper.toModel(Mockito.any(OpinionProduct.class))).thenReturn(getOpinionProductDto());
        when(opinionProductMapper.toEntity(Mockito.any(OpinionProductDTO.class))).thenReturn(getOpinionProduct());
        assertTrue(Objects.nonNull(opinionProductService.update(getOpinionProductDto())));
    }

    @Test
    void deleteByIdTest() {
        when(opinionProductRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getOpinionProduct()));
        assertTrue(opinionProductService.deleteById(ID));
    }


    private OpinionProduct getOpinionProduct() {
        OpinionProduct opinionProduct = new OpinionProduct();
        opinionProduct.setId(ID);
        opinionProduct.setProduct(getProduct());
        opinionProduct.setUser(getUser());
        return opinionProduct;
    }

    private Product getProduct() {
        Product product = new Product();
        product.setId(ID);
        product.setCode("123456W");
        product.setPartNumber("A456753");
        return product;
    }

    private User getUser(){
        User user = new User();
        user.setId(ID);
        return user;
    }

    private OpinionProductDTO getOpinionProductDto() {
        return OpinionProductDTO.builder()
                .id(ID)
                .productId(ID)
                .assessment(ASSESSMENT)
                .comment(COMMENT)
                .build();
    }

}
