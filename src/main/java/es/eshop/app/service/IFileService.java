package es.eshop.app.service;

import es.eshop.app.entity.File;

public interface IFileService {

    File getById(Long id);

    File save(File file);

    Boolean deleteById(Long id);

}
