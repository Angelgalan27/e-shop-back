package es.eshop.app.mapper;

import es.eshop.app.entity.File;
import es.eshop.app.entity.Product;
import es.eshop.app.entity.TranslationProduct;
import es.eshop.app.model.TranslationProductDTO;
import es.eshop.app.model.FileDTO;
import es.eshop.app.model.ProductDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true)
)
public interface IProductMapper extends IBaseMapper<ProductDTO, Product> {

    @Override
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductDTO toModel(Product source);

    @Override
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "categoryName", target = "category.name")
    Product toEntity(ProductDTO source);

    @Mapping(target = "languageId", source = "language.id")
    @Mapping(target = "languageName", source = "language.name")
    TranslationProductDTO toTranslationProductDTO(TranslationProduct source);

    List<TranslationProductDTO> toListTranslationProductDTO(List<TranslationProduct> source);

    @Mapping(target = "language.id", source = "languageId")
    @Mapping(target = "language.name", source = "languageName")
    TranslationProduct toTranslationProduct(TranslationProductDTO source);

    List<TranslationProduct> toListTranslationProduct(List<TranslationProductDTO> source);

    File toFile(FileDTO source);

    List<File> toListFile(List<FileDTO> source);

    FileDTO toFileDTO(File source);

    List<FileDTO> toListFileDTO(List<File> source);

    @BeforeMapping
    default void beforeMappingToEntity(@MappingTarget Product target, ProductDTO productDTO) {
        if (!productDTO.getTranslations().isEmpty()) {
            List<TranslationProduct> listTranslation = new ArrayList<>();
            productDTO.getTranslations().forEach(translation -> {
                TranslationProduct translationProduct = toTranslationProduct(translation);
                translationProduct.setProduct(target);
                listTranslation.add(translationProduct);
            });
            target.setTranslations(listTranslation);
        }
    }
}
