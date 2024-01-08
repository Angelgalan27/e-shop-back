package es.eshop.app.model;

import lombok.Data;

@Data
public class UserAddressDTO {

    private Long id;

    private String street;

    private Integer phone;

    private Boolean main;

    private Integer cp;

    private String userAddressName;

    private String userAddressSurname;

    private String observations;

    private Long provinceId;

    private String provinceName;

    private Long cityId;

    private String cityName;

    private Long countryId;

    private String countryName;
}
