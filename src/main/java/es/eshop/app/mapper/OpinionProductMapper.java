package es.eshop.app.mapper;

import es.eshop.app.entity.OpinionProduct;
import es.eshop.app.model.OpinionProductDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true)
)
public interface OpinionProductMapper extends IBaseMapper<OpinionProductDTO, OpinionProduct> {

    @Override
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    OpinionProductDTO toModel(OpinionProduct source);

    @Override
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product.id", source = "productId")
    OpinionProduct toEntity(OpinionProductDTO source);
}
