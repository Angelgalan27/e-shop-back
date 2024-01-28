package es.eshop.app.serviceImplTest;

import es.eshop.app.BaseConfigTests;
import es.eshop.app.entity.Product;
import es.eshop.app.exception.BadRequestException;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.IProductMapper;
import es.eshop.app.model.FilterProductDTO;
import es.eshop.app.model.ProductDTO;
import es.eshop.app.model.TranslationProductDTO;
import es.eshop.app.repository.IProductRepository;
import es.eshop.app.service.IFileService;
import es.eshop.app.service.IProductService;
import es.eshop.app.service.IS3Service;
import es.eshop.app.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.function.Function;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


class ProductServiceImplTest extends BaseConfigTests {

    private final Long ID = 1L;

    private final String NAME = "name";

    private final String CODE = "code";

    private final String PART_NUMBER = "part number";


    @InjectMocks
    private IProductService productService = new ProductServiceImpl(null, null, null, null);

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IProductMapper productMapper;

    @Mock
    private IS3Service s3Service;

    @Mock
    private IFileService fileService;
    @BeforeEach
    void init() {
        productService = new ProductServiceImpl(productRepository, productMapper, s3Service, fileService);
    }

    @Test
    void getAllByFilterTest(){
        when(productRepository.getAllByFilter(any(FilterProductDTO.class), any(Pageable.class))).thenReturn(getPageProduct());
        when(productMapper.toListModel(Mockito.anyList())).thenReturn(Collections.singletonList(getProductDTO()));
        assertFalse(productService.getAllByFilter(getFilterProductDTO()).getProducts().isEmpty());
    }
    @Test
    void getByIdTest(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct()));
        when(productMapper.toModel(any(Product.class))).thenReturn(getProductDTO());
        assertTrue(Objects.nonNull(productService.getById(ID)));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            productService.getById(ID);
        } catch (NotFoundException e) {
            assertEquals("El producto con el id "+ID+" no existe.", e.getMessage());
        }
    }
    @Test
    void saveTest(){
        when(productRepository.save(any(Product.class))).thenReturn(getProduct());
        when(productMapper.toModel(any(Product.class))).thenReturn(getProductDTO());
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(getProduct());
        assertTrue(Objects.nonNull(productService.save(getProductDTO())));

        ProductDTO productDTO = getProductDTO();
        productDTO.setName(null);

        try {
            productService.save(productDTO);
        } catch (BadRequestException e) {
            assertEquals("El campo nombre es obligatorio.", e.getMessage());
        }

        productDTO = getProductDTO();
        productDTO.setCode(null);
        try {
            productService.save(productDTO);
        } catch (BadRequestException e) {
            assertEquals("El campo código es obligatorio.", e.getMessage());
        }

        productDTO = getProductDTO();
        productDTO.setPartNumber(null);
        try {
            productService.save(productDTO);
        } catch (BadRequestException e) {
            assertEquals("El campo número de producto es obligatorio.", e.getMessage());
        }
    }
    @Test
    void updateTest(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct()));
        when(productRepository.save(any(Product.class))).thenReturn(getProduct());
        when(productMapper.toModel(any(Product.class))).thenReturn(getProductDTO());
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(getProduct());
        assertTrue(Objects.nonNull(productService.update(getProductDTO())));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            productService.update(getProductDTO());
        } catch (NotFoundException e) {
            assertEquals("El producto con el id "+ID+" no existe.", e.getMessage());
        }
    }

    @Test
    void deleteByIdTest(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct()));
        assertTrue(productService.deleteById(ID));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            productService.update(getProductDTO());
        } catch (NotFoundException e) {
            assertEquals("El producto con el id "+ID+" no existe.", e.getMessage());
        }
    }

    private FilterProductDTO getFilterProductDTO() {
        return FilterProductDTO.builder()
                .name(NAME)
                .code(CODE)
                .build();
    }



    private Product getProduct() {
        Product product = new Product();
        product.setId(ID);
        product.setName(NAME);
        product.setCode(CODE);
        product.setPartNumber(PART_NUMBER);
        return product;
    }

    private ProductDTO getProductDTO() {
        return ProductDTO.builder()
                .id(ID)
                .name(NAME)
                .code(CODE)
                .partNumber(PART_NUMBER)
                .categoryId(ID)
                .stock(1)
                .translations(Collections.singletonList(getTranslationDTO()))
                .build();
    }

    private TranslationProductDTO getTranslationDTO(){
        return TranslationProductDTO.builder()
                .characteristics("")
                .specifications("")
                .description("")
                .languageId(1L)
                .build();
    }

    private Page<Product> getPageProduct() {
        return new Page<Product>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 1;
            }

            @Override
            public int getSize() {
                return 1;
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<Product> getContent() {
                return Collections.singletonList(getProduct());
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Product> iterator() {
                return new Iterator<Product>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public Product next() {
                        return getProduct();
                    }
                };
            }
        };
    }
}
