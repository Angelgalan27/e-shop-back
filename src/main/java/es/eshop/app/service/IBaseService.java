package es.eshop.app.service;

import java.util.List;

public interface IBaseService<M, ID> {

    List<M> getAll();

    M getById(ID id);

    M save(M m);

    M update(M m);

    Boolean deleteById(ID id);
}
