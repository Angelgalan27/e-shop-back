package es.eshop.app.service;

import es.eshop.app.model.OpinionProductDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IOpinionProductService extends IBaseService<OpinionProductDTO, Long> {

    List<OpinionProductDTO> getAllByUserAndProductId(@NotNull Long userId, @NotNull Long productId);

    List<OpinionProductDTO> getAllByUser(@NotNull Long userId);

    List<OpinionProductDTO> getAllByProductId(@NotNull Long productId);

}
