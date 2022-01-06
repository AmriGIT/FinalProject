/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.repository;

import co.amri.tokoshop.tokoshop.entity.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author LENOVO
 */
public interface ProductCategoryRepository extends
        PagingAndSortingRepository<Product, Integer> {

    public List<Product> findByCategoryIdContains(
            Integer categoryid, Pageable pageable);
}
