/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.repository;

import co.amri.tokoshop.tokoshop.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author LENOVO
 */
public interface ProductRepository extends CrudRepository<Product, Integer>{
    @Override
    @Cacheable(value = "getProducts")
    public Iterable<Product> findAll();
    
    @Override
    @Cacheable(value = "getProductById", key = "#id")
    public Optional<Product> findById(Integer id);
    
    public Product findByid(Integer id);
}
