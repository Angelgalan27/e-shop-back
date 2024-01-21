package es.eshop.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class FilterProductDTO  extends BaseResponsePaginationDTO{

    private String name;

    private String code;

    private String partNumber;
}
