package es.eshop.app.mapper;


import java.util.List;


public interface IBaseMapper<M, E> {

    M toModel(E source);

    E toEntity( M source);

    List<M> toListModel(List<E> source);

    List<E> toListEntity(List<M> source);
}
