package es.eshop.app.serviceImplTest;

import es.eshop.app.entity.Category;
import es.eshop.app.entity.Language;
import es.eshop.app.entity.TranslationCategory;
import es.eshop.app.exception.BadRequestException;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.ICategoryMapper;
import es.eshop.app.model.CategoryDTO;
import es.eshop.app.model.TranslationCategoryDTO;
import es.eshop.app.repository.ICategoryRepository;
import es.eshop.app.service.ICategoryService;
import es.eshop.app.service.impl.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryServiceImplTest {

    private final Long ID = 1L;

    private final String NAME = "name";

    private final String DESCRIPTION = "description";

    @InjectMocks
    private ICategoryService categoryService = new CategoryServiceImpl(null, null);

    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private ICategoryMapper categoryMapper;


    @BeforeEach
    void init() {
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    void getAllTest() {
        Mockito.when(categoryRepository.findAll()).thenReturn(getListCategories());
        Mockito.when(categoryMapper.toListModel(Mockito.anyList())).thenReturn(Collections.singletonList(getCategoryDto()));
        assertFalse(categoryService.getAll().isEmpty());
    }

    @Test
    void getByIdTest() {
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getCategory()));
        Mockito.when(categoryMapper.toModel(Mockito.any(Category.class))).thenReturn(getCategoryDto());
        assertTrue(Objects.nonNull(categoryService.getById(ID)));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        try {
            categoryService.getById(ID);
        } catch (NotFoundException e) {
            assertEquals("La categoría con el id "+ID+" no existe.", e.getMessage());
        }
    }

    @Test
    void saveTest() {
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(getCategory());
        Mockito.when(categoryMapper.toModel(Mockito.any(Category.class))).thenReturn(getCategoryDto());
        Mockito.when(categoryMapper.toEntity(Mockito.any(CategoryDTO.class))).thenReturn(getCategory());
        assertTrue(Objects.nonNull(categoryService.save(getCategoryDto())));
        CategoryDTO categoryDTO = getCategoryDto();
        categoryDTO.setName(null);

        try {
            categoryService.save(categoryDTO);
        } catch (BadRequestException e) {
            assertEquals("El nombre de la categoría es obligatorio.", e.getMessage());
        }

    }
    @Test
    void updateTest() {
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getCategory()));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(getCategory());
        Mockito.when(categoryMapper.toModel(Mockito.any(Category.class))).thenReturn(getCategoryDto());
        assertTrue(Objects.nonNull(categoryService.update(getCategoryDto())));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        try {
            categoryService.update(getCategoryDto());
        } catch (NotFoundException e) {
            assertEquals("La categoría con el id "+ID+" no existe.", e.getMessage());
        }
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getCategory()));
        CategoryDTO categoryDTO = getCategoryDto();
        categoryDTO.setParentId(2L);
        assertTrue(Objects.nonNull(categoryService.update(categoryDTO)));
    }

    @Test
    void deleteByIdTest() {
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getCategory()));
        assertTrue(categoryService.deleteById(ID));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try {
            categoryService.deleteById(ID);
        } catch (NotFoundException e) {
            assertEquals("La categoría con el id "+ID+" no existe.", e.getMessage());
        }
    }

    private List<Category> getListCategories() {
        return Collections.singletonList(getCategory());
    }

    private Category getCategory() {
        Category category = new Category();
        category.setTranslations(Collections.singletonList(getTranslationCategory()));
        return category;
    }

    private TranslationCategory getTranslationCategory() {
        TranslationCategory translationCategory = new TranslationCategory();
        translationCategory.setName(NAME);
        translationCategory.setDescription(DESCRIPTION);
        translationCategory.setLanguage(getLanguage());
        return translationCategory;
    }

    private Language getLanguage() {
        Language language = new Language();
        language.setCode("es_ES");
        language.setName("spain");
        return language;
    }

    private CategoryDTO getCategoryDto() {
        return CategoryDTO.builder()
                .id(ID)
                .name(NAME)
                .translations(Collections.singletonList(TranslationCategoryDTO.builder()
                                .description(DESCRIPTION)
                                .name(NAME)
                        .build()))
                .build();
    }
}
