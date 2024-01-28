package es.eshop.app.service.impl;

import es.eshop.app.entity.OpinionProduct;
import es.eshop.app.entity.Product;
import es.eshop.app.entity.User;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.mapper.IProductMapper;
import es.eshop.app.mapper.IUserMapper;
import es.eshop.app.mapper.OpinionProductMapper;
import es.eshop.app.model.OpinionProductDTO;
import es.eshop.app.model.ProductDTO;
import es.eshop.app.model.UserDTO;
import es.eshop.app.repository.IUserRepository;
import es.eshop.app.repository.OpinionProductRepository;
import es.eshop.app.service.IOpinionProductService;
import es.eshop.app.service.IProductService;
import es.eshop.app.service.IUserService;
import es.eshop.app.util.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OpinionProductServiceImpl implements IOpinionProductService {

    private final OpinionProductRepository opinionProductRepository;

    private final IUserService userService;

    private final IProductService productService;

    private final OpinionProductMapper opinionProductMapper;

    private final IUserMapper userMapper;

    private final IProductMapper productMapper;

    @Override
    public OpinionProductDTO getById(Long aLong) {
        return opinionProductMapper.toModel(opinionProductRepository.findById(aLong)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage("opinion.product.not.found", aLong))));
    }

    @Override
    public OpinionProductDTO save(OpinionProductDTO opinionProductDTO) {
        return opinionProductMapper.toModel(opinionProductRepository.save(
                opinionProductMapper.toEntity(opinionProductDTO)
        ));
    }

    @Override
    public OpinionProductDTO update(OpinionProductDTO opinionProductDTO) {
        OpinionProduct opinionProduct = opinionProductRepository.findById(opinionProductDTO.getId())
                .orElseThrow(() -> new NotFoundException(Resource.getMessage("opinion.product.not.found", opinionProductDTO.getId())));
        mergeOpinionProduct(opinionProduct, opinionProductDTO);
        return opinionProductMapper.toModel(opinionProductRepository.save(opinionProduct));
    }

    @Override
    public Boolean deleteById(Long aLong) {
        OpinionProduct opinionProduct = opinionProductRepository.findById(aLong)
                .orElseThrow(() -> new NotFoundException(Resource.getMessage("opinion.product.not.found", aLong)));
        opinionProductRepository.delete(opinionProduct);
        return Boolean.TRUE;
    }

    @Override
    public List<OpinionProductDTO> getAllByUserAndProductId(Long userId, Long productId) {
        return opinionProductMapper.toListModel(opinionProductRepository
                .findByUserAndProduct(userMapper.toEntity(userService.getById(userId)),
                        productMapper.toEntity(productService.getById(productId))));
    }

    @Override
    public List<OpinionProductDTO> getAllByUser(Long userId) {
        return opinionProductMapper.toListModel(opinionProductRepository.findByUser(userMapper.toEntity(userService.getById(userId))));
    }

    @Override
    public List<OpinionProductDTO> getAllByProductId(Long productId) {
        return opinionProductMapper.toListModel(opinionProductRepository.findByProduct(productMapper.toEntity(productService.getById(productId))));
    }


    private void mergeOpinionProduct(OpinionProduct opinionProduct, OpinionProductDTO opinionProductDTO) {
        if (StringUtils.isNotBlank(opinionProductDTO.getComment())) {
            opinionProduct.setComment(opinionProductDTO.getComment());
        }
        if (Objects.nonNull(opinionProductDTO.getAssessment())) {
            opinionProduct.setAssessment(opinionProductDTO.getAssessment());
        }
    }
}
