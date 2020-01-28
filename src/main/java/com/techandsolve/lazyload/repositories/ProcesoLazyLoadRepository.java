package com.techandsolve.lazyload.repositories;

import com.techandsolve.lazyload.entities.ProcesoLazyLoad;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProcesoLazyLoadRepository extends MongoRepository<ProcesoLazyLoad,String> {
    List<ProcesoLazyLoad> findByIdTrabajador(String idTrabajador);
}
