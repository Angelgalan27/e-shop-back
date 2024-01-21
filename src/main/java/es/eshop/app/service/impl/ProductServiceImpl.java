package es.eshop.app.service.impl;

import es.eshop.app.entity.Category;
import es.eshop.app.entity.File;
import es.eshop.app.entity.Product;
import es.eshop.app.exception.BadRequestException;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.IProductMapper;
import es.eshop.app.model.FilterProductDTO;
import es.eshop.app.model.ProductDTO;
import es.eshop.app.model.ResponseProductPaginationDTO;
import es.eshop.app.repository.IProductRepository;
import es.eshop.app.service.IFileService;
import es.eshop.app.service.IProductService;
import es.eshop.app.service.IS3Service;
import es.eshop.app.util.Resource;
import es.eshop.app.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private static final String BASE_PATH_FILE = "product/";

    private static final  String PRODUCT_NOT_FOUND = "product.not.found";

    private final IProductRepository productRepository;

    private final IProductMapper productMapper;

    private final IS3Service s3Service;

    private final IFileService fileService;

    /**
     * Get all product by filter.
     * @param filter
     * @return
     */
    @Override
    public ResponseProductPaginationDTO getAllByFilter(FilterProductDTO filter) {
        Pageable pageable = Utils.getPageable(filter.getPage(), filter.getNumberResultPage(), filter.getSort(), filter.getSortOrder());
        Page<Product> products = productRepository.getAllByFilter(filter, pageable);
        return  ResponseProductPaginationDTO.builder()
                .products(productMapper.toListModel(products.stream().toList()))
                .page(filter.getPage())
                .totalResult(products.stream().toList().size())
                .numberResultPage(filter.getNumberResultPage())
                .build();
    }

    /**
     * upload file product
     * @param files
     * @param productId
     * @return
     */
    @Override
    public Boolean uploadFile(List<MultipartFile> files, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage(PRODUCT_NOT_FOUND, productId)));
        files.forEach(multipartFile -> {
            File file = new File();
            file.setName(multipartFile.getOriginalFilename());
            file.setPath(BASE_PATH_FILE + product.getId() + "/" + multipartFile.getOriginalFilename());
            product.getFiles().add(fileService.save(file));
            s3Service.updateFile(multipartFile, file.getPath());
        });
        productRepository.save(product);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean deleteFile(Long productId, Long fileId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage(PRODUCT_NOT_FOUND, productId)));
        Optional<File> file = product.getFiles().stream().filter(fileP -> fileP.getId().equals(fileId)).findFirst();
        if (file.isPresent()) {
            fileService.deleteById(file.get().getId());
            s3Service.deleteFile(file.get().getPath());
            product.getFiles().remove(file.get());
            productRepository.save(product);
        }
        return Boolean.TRUE;
    }

    /**
     * get product by Id
     * @param aLong
     * @return
     */
    @Override
    public ProductDTO getById(Long aLong) {
        return productMapper.toModel(productRepository.findById(aLong)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage(PRODUCT_NOT_FOUND, aLong)) ));
    }

    /**
     * save product
     * @param productDTO
     * @return
     */
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        if(StringUtils.isBlank(productDTO.getName())) {
            throw new BadRequestException(Resource.getMessage("product.name.mandatory"));
        }
        if(StringUtils.isBlank(productDTO.getCode())) {
            throw new BadRequestException(Resource.getMessage("product.code.mandatory"));
        }
        if(StringUtils.isBlank(productDTO.getPartNumber())) {
            throw new BadRequestException(Resource.getMessage("product.part.number.mandatory"));
        }
        return productMapper.toModel(productRepository.save(
                productMapper.toEntity(productDTO)
        ));
    }

    /**
     * update product
     * @param productDTO
     * @return
     */
    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new NotFoundException(Resource.getMessage(PRODUCT_NOT_FOUND, productDTO.getId())));
        mergeProduct(product, productDTO);
        return productMapper.toModel(productRepository.save(product));
    }

    /**
     * Delete product by id.
     *
     * @param aLong
     * @return
     */
    @Override
    public Boolean deleteById(Long aLong) {
        Product product = productRepository.findById(aLong)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage(PRODUCT_NOT_FOUND, aLong)) );
        productRepository.delete(product);
        return Boolean.TRUE;
    }

    /**
     * merge product.
     * @param product
     * @param productDTO
     */
    private void mergeProduct(Product product, ProductDTO productDTO) {
        if (StringUtils.isNotBlank(productDTO.getName())) {
            product.setName(productDTO.getName());
        }

        if (StringUtils.isNotBlank(productDTO.getCode())) {
            product.setCode(productDTO.getCode());
        }

        if (StringUtils.isNotBlank(productDTO.getPartNumber())) {
            product.setPartNumber(productDTO.getPartNumber());
        }

        if (Objects.nonNull(productDTO.getStock())) {
            product.setStock(productDTO.getStock());
        }

        if (Objects.nonNull(productDTO.getCategoryId())) {
            Category category = new Category();
            category.setId(productDTO.getCategoryId());
            product.setCategory(category);
        }

        if(!productDTO.getTranslations().isEmpty()) {
            product.setTranslations(productMapper.toListTranslationProduct(productDTO.getTranslations()));
            product.getTranslations().forEach(translation -> translation.setProduct(product));
        }

    }

}
