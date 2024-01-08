package es.eshop.app.mapper;

import es.eshop.app.entity.User;
import es.eshop.app.entity.UserAddress;
import es.eshop.app.model.UserAddressDTO;
import es.eshop.app.model.UserDTO;
import org.mapstruct.*;

import java.util.Objects;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true)
)
public interface IUserMapper extends IBaseMapper<UserDTO, User>{

    @Mapping(target = "provinceId", source = "city.province.id")
    @Mapping(target = "provinceName", source = "city.province.name")
    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "countryId", source = "city.province.country.id")
    @Mapping(target = "countryName", source = "city.province.country.name")
    UserAddressDTO toAddressDto(UserAddress source);

    @Mapping(target = "city.id", source = "cityId")
    UserAddress toAddress(UserAddressDTO source);

    @AfterMapping
    default void afterUserMapper(@MappingTarget User target, UserDTO source) {
        if (!target.getAddresses().isEmpty()) {
            target.getAddresses().forEach(address -> {
                if(Objects.isNull(address.getUser())) {
                    address.setUser(target);
                }
            });
        }
    }
}
