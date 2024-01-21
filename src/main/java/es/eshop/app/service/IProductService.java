package es.eshop.app.service;

import es.eshop.app.model.FilterProductDTO;
import es.eshop.app.model.ProductDTO;
import es.eshop.app.model.ResponseProductPaginationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService extends IBaseService<ProductDTO, Long> {

        ResponseProductPaginationDTO getAllByFilter(FilterProductDTO filter);

        Boolean uploadFile(List<MultipartFile> files, Long productId);

        Boolean deleteFile(Long productId, Long fileId);
}
