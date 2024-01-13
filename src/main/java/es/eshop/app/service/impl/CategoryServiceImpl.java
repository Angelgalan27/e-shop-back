package es.eshop.app.service.impl;

import es.eshop.app.entity.Category;
import es.eshop.app.entity.TranslationCategory;
import es.eshop.app.exception.BadRequestException;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.ICategoryMapper;
import es.eshop.app.model.CategoryDTO;
import es.eshop.app.repository.ICategoryRepository;
import es.eshop.app.service.ICategoryService;
import es.eshop.app.util.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private static final  String CATEGORY_NOT_FOUND = "category.not.found";

    private final ICategoryRepository categoryRepository;

    private final ICategoryMapper categoryMapper;


    @Override
    public List<CategoryDTO> getAll() {
        return categoryMapper.toListModel(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO getById(Long aLong) {
        return categoryMapper.toModel(categoryRepository.findById(aLong)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage(CATEGORY_NOT_FOUND, aLong)) ));
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        if (Objects.isNull(categoryDTO.getName())) {
            throw new BadRequestException(Resource.getMessage("category.translation.name.mandatory"));
        }

        Category category = categoryMapper.toEntity(categoryDTO);
        List<TranslationCategory> listTranslation = category.getTranslations();
        category.setTranslations(null);

        final Category categoryDb = categoryRepository.save(category);

        if (Objects.nonNull(listTranslation) && !listTranslation.isEmpty()) {
            listTranslation.forEach(translation -> translation.setCategory(categoryDb));
            categoryDb.setTranslations(listTranslation);
        }

        return categoryMapper.toModel(categoryRepository.save(categoryDb));
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new NotFoundException(Resource.getMessage(CATEGORY_NOT_FOUND, categoryDTO.getId())));
        mergeCategory(categoryDTO, category);
        return categoryMapper.toModel(categoryRepository.save(category));
    }

    @Override
    public Boolean deleteById(Long aLong) {
        Category category = categoryRepository.findById(aLong)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage(CATEGORY_NOT_FOUND, aLong)));
        categoryRepository.delete(category);
        return Boolean.TRUE;
    }

    private void mergeCategory(CategoryDTO categoryDTO, Category category){
        if (Objects.nonNull(categoryDTO.getTranslations()) && !categoryDTO.getTranslations().isEmpty()) {
            category.setTranslations(categoryMapper.toListTranslationCategory(categoryDTO.getTranslations()));
            category.getTranslations().forEach(translation -> translation.setCategory(category));
        }
        if (Objects.nonNull(categoryDTO.getParentId())) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new NotFoundException(Resource.getMessage("category.parent.not.found", categoryDTO.getParentId())));
            category.setParentCategory(parentCategory);
        }
    }
}
