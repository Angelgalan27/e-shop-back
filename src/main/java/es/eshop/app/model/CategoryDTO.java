package es.eshop.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    private Long parentId;

    @Builder.Default
    private List<TranslationCategoryDTO> translation = new ArrayList<>();
}
