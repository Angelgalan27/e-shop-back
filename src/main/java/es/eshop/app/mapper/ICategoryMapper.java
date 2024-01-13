package es.eshop.app.mapper;

import es.eshop.app.entity.Category;
import es.eshop.app.entity.TranslationCategory;
import es.eshop.app.model.CategoryDTO;
import es.eshop.app.model.TranslationCategoryDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true)
)
public interface ICategoryMapper extends IBaseMapper<CategoryDTO, Category> {

    @Override
    @Mapping(target = "parentId", source = "parentCategory.id")
    @Mapping(target = "parentName", source = "parentCategory.name")
    CategoryDTO toModel(Category source);

    @Override
    Category toEntity(CategoryDTO source);

    @Mapping(target = "languageId", source = "language.id")
    @Mapping(target = "languageName", source = "language.name")
    @Mapping(target = "languageCode", source = "language.code")
    TranslationCategoryDTO toTranslationCategoryDto(TranslationCategory source);

    @Mapping(target = "language.id", source = "languageId")
    @Mapping(target = "language.name", source = "languageName")
    @Mapping(target = "language.code",source = "languageCode")
    TranslationCategory toTranslationCategory(TranslationCategoryDTO source);

    List<TranslationCategory> toListTranslationCategory(List<TranslationCategoryDTO> source);


    @AfterMapping
    default void afterMapping(@MappingTarget Category category, CategoryDTO source) {
        if (Objects.nonNull(source.getParentId())) {
            Category parentCategory = new Category();
            parentCategory.setId(source.getParentId());
            category.setParentCategory(parentCategory);
        }
    }
}
