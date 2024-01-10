package es.eshop.app.service.impl;

import es.eshop.app.entity.Category;
import es.eshop.app.exception.BadRequestException;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.ICategoryMapper;
import es.eshop.app.model.CategoryDTO;
import es.eshop.app.repository.ICategoryRepository;
import es.eshop.app.service.ICategoryService;
import es.eshop.app.util.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private static final  String CATEGORY_NOT_FOUND = "category.not.found";

    private final ICategoryRepository categoryRepository;

    private final ICategoryMapper categoryMapper;

    public CategoryServiceImpl(ICategoryRepository categoryRepository,
                               ICategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

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
        if (Objects.isNull(categoryDTO.getTranslation()) || categoryDTO.getTranslation().isEmpty()) {
            throw new BadRequestException(Resource.getMessage("category.translation.mandatory"));
        }
        categoryDTO.getTranslation().forEach(translation -> {
            if(StringUtils.isBlank(translation.getName())) {
                throw new BadRequestException(Resource.getMessage("category.translation.name.mandatory"));
            }
        });
        return categoryMapper.toModel(categoryRepository.save(
                categoryMapper.toEntity(categoryDTO)
        ));
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
        if (Objects.nonNull(categoryDTO.getTranslation()) && !categoryDTO.getTranslation().isEmpty()) {
            category.setTranslations(categoryMapper.toListTranslationCategory(categoryDTO.getTranslation()));
        }
        if (Objects.nonNull(categoryDTO.getParentId())) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new NotFoundException(Resource.getMessage("category.parent.not.found", categoryDTO.getParentId())));
            category.setParentCategory(parentCategory);
        }
    }
}
