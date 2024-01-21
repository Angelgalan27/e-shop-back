package es.eshop.app.service.impl;

import es.eshop.app.entity.File;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.repository.FileRepository;
import es.eshop.app.service.IFileService;
import es.eshop.app.util.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements IFileService {

    private final FileRepository fileRepository;

    @Override
    public File getById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage("file.not.found", id)));
    }

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public Boolean deleteById(Long id) {
        fileRepository.deleteById(id);
        return Boolean.TRUE;
    }
}
