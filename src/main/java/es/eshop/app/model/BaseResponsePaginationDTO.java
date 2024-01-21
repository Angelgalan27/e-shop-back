package es.eshop.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseResponsePaginationDTO {

    private Integer page;

    private Integer totalResult;

    private Integer numberResultPage;

    private String sort;

    private String sortOrder;
}
