package es.eshop.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TranslationCategoryDTO {

    private Long languageId;

    private String languageName;

    private String name;

    private String description;
}
