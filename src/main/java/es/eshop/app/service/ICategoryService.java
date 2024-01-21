package es.eshop.app.service;

import es.eshop.app.model.CategoryDTO;

import java.util.List;

public interface ICategoryService extends IBaseService<CategoryDTO, Long> {

    List<CategoryDTO> getAll();
}
