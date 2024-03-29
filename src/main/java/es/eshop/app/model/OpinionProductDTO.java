package es.eshop.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OpinionProductDTO {

    private Long id;

    private Long userId;

    private Long productId;

    private String comment;

    private Integer assessment;
}
