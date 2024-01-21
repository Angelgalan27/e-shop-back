package es.eshop.app.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
public class ResponseProductPaginationDTO extends BaseResponsePaginationDTO {

    @Builder.Default
    List<ProductDTO> products = new ArrayList<>();
}
