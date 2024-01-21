package es.eshop.app.service;

import java.util.Collections;
import java.util.List;

public interface IBaseService<M, ID> {


    M getById(ID id);

    M save(M m);

    M update(M m);

    Boolean deleteById(ID id);
}
