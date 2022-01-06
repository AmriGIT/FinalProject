/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.controller;

import co.amri.tokoshop.tokoshop.entity.Cart;
import co.amri.tokoshop.tokoshop.entity.CartItem;
import co.amri.tokoshop.tokoshop.entity.Product;
import co.amri.tokoshop.tokoshop.entity.User;
import co.amri.tokoshop.tokoshop.repository.CartItemRespository;
import co.amri.tokoshop.tokoshop.repository.CartRespository;
import co.amri.tokoshop.tokoshop.repository.ProductRepository;
import co.amri.tokoshop.tokoshop.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
public class CartController {

    @Autowired
    private CartRespository cartRespository;

    @Autowired
    private CartItemRespository cartItemRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    public static final String STATUS_ACTIVE = "ACTIVE";

    @PostMapping("/cart/")
    public ResponseEntity<String> saveCart(@RequestBody CartItem cartItem,
            Principal principal) {
        String userName = principal.getName();
        User user = userRepository.getUserByName(userName);
        Optional<Cart> optionalCart = cartRespository.findCartByUserAndStatus(user, STATUS_ACTIVE);
        Optional<CartItem> optionalCartItem = cartItemRespository.findByProduct(cartItem.getProduct());
        if (optionalCart.isEmpty() && optionalCartItem.isEmpty()) {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setStatus("ACTIVE");
            cartRespository.save(cart);
            Cart activeCart = cartRespository.findCartByStatusAndUser(STATUS_ACTIVE, cart.getUser());
            Product product = productRepository.findByid(cartItem.getProduct().getId());
            if (activeCart != null && product != null) {
                if (product.getStock() >= cartItem.getQuantity()) {
                    product.setStock(product.getStock() - cartItem.getQuantity());
                    productRepository.save(product);
                    cartItem.setProduct(product);
                    cartItem.setCart(cart);
                    cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
                    cartItemRespository.save(cartItem);
                    return ResponseEntity.ok("ok");
                } else {
                    return ResponseEntity.badRequest().body("Quantiti Melebihi dari Stock");
                }

            }
        } else {
          Cart c = optionalCart.get();
            Product product = productRepository.findByid(cartItem.getProduct().getId());
            if (c.getUser() != null && product != null && optionalCartItem !=null) {
                if (product.getStock() >= cartItem.getQuantity()) {
                    product.setStock(product.getStock() - cartItem.getQuantity());
                    productRepository.save(product);
                    cartItem.setProduct(product);
                    cartItem.setCart(c);
                    cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
                    cartItemRespository.save(cartItem);
                    return ResponseEntity.ok("ok");
                } else {
                    return ResponseEntity.badRequest().body("Quantiti Melebihi dari Stock");
                }

            }

        }


        return ResponseEntity.badRequest().body("Invalid Parameter");

    }

    @PutMapping("/cart/")
    public ResponseEntity<String> updateCart(@RequestBody Cart cart,
            CartItem cartItem,
            Principal principal) {
        String userName = principal.getName();
        User user = userRepository.getUserByName(userName);
        if (user != null) {
            Optional<Cart> optionalCart = cartRespository.findById(cart.getId());
            if (!optionalCart.isEmpty()) {
                Cart c = optionalCart.get();
                if (c != null
                        && c.getUser() != null
                        && user.getId().equals(c.getUser().getId())) {

//                    c.setStatus(cart.getStatus());
                    cart.setUser(user);
                    cart.setTransactionDate(new Date());
                    cartRespository.save(cart);
                    return ResponseEntity.ok("ok");
                }
            }
        }
        return ResponseEntity.badRequest()
                .body("Invalid Parameter");
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> deleteCartById(
            @PathVariable(name = "id") Integer id,
            Principal principal) {
        String userName = principal.getName();
        User user = userRepository.getUserByName(userName);
        if (user != null) {
            Cart cart = cartRespository.findById(id).get();
            if (cart != null
                    && cart.getUser() != null
                    && cart.getUser().getId().equals(user.getId())) {
                cartRespository.deleteById(id);
                return ResponseEntity.ok("ok");
            }
        }
        return ResponseEntity.
                badRequest().
                body("Invalid Parameter");
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartItem>> getCart() {
        Iterable<CartItem> carts = cartItemRespository.findAll();
        List<CartItem> cartList = new ArrayList<>();
        for (CartItem cart : carts) {
            cartList.add(cart);

        }
        return ResponseEntity.ok(cartList);
    }

}
