package es.eshop.app.mapper;

import es.eshop.app.entity.Category;
import es.eshop.app.entity.TranslationCategory;
import es.eshop.app.model.CategoryDTO;
import es.eshop.app.model.TranslationCategoryDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true)
)
public interface ICategoryMapper extends IBaseMapper<CategoryDTO, Category> {

    @Override
    @Mapping(target = "parentId", source = "parentCategory.id")
    CategoryDTO toModel(Category source);

    @Override
    @Mapping(target = "parentCategory.id", source = "parentId")
    Category toEntity(CategoryDTO source);

    @Mapping(target = "languageId", source = "language.id")
    @Mapping(target = "languageName", source = "language.name")
    TranslationCategoryDTO toTranslationCategoryDto(TranslationCategory source);

    @Mapping(target = "language.id", source = "languageId")
    @Mapping(target = "language.name", source = "languageName")
    TranslationCategory toTranslationCategory(TranslationCategoryDTO source);

    List<TranslationCategory> toListTranslationCategory(List<TranslationCategoryDTO> source);
}
