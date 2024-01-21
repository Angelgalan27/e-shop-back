package es.eshop.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private Long categoryId;

    private String categoryName;

    private String name;

    private String code;

    private String partNumber;

    private Integer stock;

    @Builder.Default
    private List<FileDTO> files = new ArrayList<>();

    @Builder.Default
    private List<TranslationProductDTO> translations = new ArrayList<>();
}
