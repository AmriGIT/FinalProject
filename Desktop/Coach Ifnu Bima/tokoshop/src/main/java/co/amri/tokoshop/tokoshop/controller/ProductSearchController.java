/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.controller;

import co.amri.tokoshop.tokoshop.entity.Product;
import co.amri.tokoshop.tokoshop.repository.ProductSearchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping("/api")
public class ProductSearchController {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(
            @RequestParam String query,
            @RequestParam Integer size,
            @RequestParam Integer page,
            @RequestParam String sort) {

        Pageable pageable;
        if ("PRICE_ASC".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("price"));
        } else if ("PRICE_DESC".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "price"));
        } else if ("TITLE".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("name"));
        } else {
            pageable = PageRequest.of(page, size);
        }

        List<Product> products = productSearchRepository.findByNameContains(query, pageable);
        for (Product product : products) {
            product.setUser(null);
        }
        return ResponseEntity.ok(products);
    }

}
