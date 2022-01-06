/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.controller;

import co.amri.tokoshop.tokoshop.entity.Product;
import co.amri.tokoshop.tokoshop.entity.Stock;
import co.amri.tokoshop.tokoshop.entity.User;
import co.amri.tokoshop.tokoshop.repository.ProductRepository;
import co.amri.tokoshop.tokoshop.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping("/api")
public class ProductController {

//    private ProductDao productDao;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRespository;

    //localhost:8080/api/product/
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productsList = new ArrayList<>();
        for (Product product : products) {
            productsList.add(product);
        }
//        return ResponseEntity.ok(productDao.getProducts());
        return ResponseEntity.ok(productsList);
    }

    @GetMapping("product/id/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable(name = "id") Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isEmpty()) {
            return ResponseEntity.ok(optionalProduct.get());
        }
        return ResponseEntity.badRequest().body(null);

    }

    @PostMapping("/product/")
    public ResponseEntity<String> saveProduct(@RequestBody Product product,
            Principal principal) {

        //Dummy before we WOrk on login using JWT
        String userName = principal.getName();
        User user = userRespository.getUserByName(userName);
        product.setUser(user);
        productRepository.save(product);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/product/")
    public ResponseEntity<String> updateProduct(@RequestBody Product product,
            Principal principal) {
        String userName = principal.getName();
        User user = userRespository.getUserByName(userName);
        if (user != null) {
            Optional<Product> optionalProduct = productRepository.findById(product.getId());

            if (!optionalProduct.isEmpty()) {
                Product p = optionalProduct.get();
                if (p != null
                        && p.getUser() != null
                        && user.getId().equals(p.getUser().getId())) {
                    p.setCategoryId(product.getCategoryId());
                    p.setName(product.getName());
                    p.setPrice(product.getPrice());
                    p.setStock(product.getStock());
                    product.setUser(user);
                    productRepository.save(p);
                    return ResponseEntity.ok("ok");
                }

            }

        }
        return ResponseEntity.badRequest()
                .body("Invalid Parameter or Product is not allowd to update with this user");
    }

    @DeleteMapping("/product/")
    public ResponseEntity<String> deleteProducts() {
        productRepository.deleteAll();
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(
            @PathVariable(name = "id") Integer id,
            Principal principal) {
        String userName = principal.getName();
        User user = userRespository.getUserByName(userName);
        if (user != null) {
            Product product = productRepository.findById(id).get();
            if (product != null
                    && product.getUser() != null
                    && product.getUser().getId().equals(user.getId())) {
                productRepository.deleteById(id);
                return ResponseEntity.ok("ok");
            }
        }
        return ResponseEntity.
                badRequest().
                body("Invalid Parameter or product is not allowed to update with this user");
    }

    @PutMapping("/product/stock/{id}")
    public ResponseEntity<String> updateStock(
            @PathVariable Integer id,
            @RequestBody Stock stock,
            Principal principal) {
        String userName = principal.getName();
        User user = userRespository.getUserByName(userName);
        if (user != null) {
            Product product = productRepository.findById(id).get();
            if (product != null) {
                product.setStock(stock.getStock());
                productRepository.save(product);
            }
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity
                .badRequest()
                .body("Invalid paramter or product is not allowed to update with this user");
    }
}
