/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.controller;

import co.amri.tokoshop.tokoshop.entity.Cart;
import co.amri.tokoshop.tokoshop.entity.CartItem;
import co.amri.tokoshop.tokoshop.entity.User;
import co.amri.tokoshop.tokoshop.repository.CartItemRespository;
import co.amri.tokoshop.tokoshop.repository.CartRespository;
import co.amri.tokoshop.tokoshop.repository.ProductRepository;
import co.amri.tokoshop.tokoshop.repository.UserRepository;
import java.security.Principal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping("/api/")
public class CartItemController {
    
    @Autowired
    private CartItemRespository cartItemRespository;
    
    @Autowired
    private CartRespository cartRespository;
    
    @Autowired
    private ProductRepository productRespository;
    
    @Autowired
    private UserRepository userRespoRepository;
    @PostMapping("/cartitem/")
    public ResponseEntity<String> saveCartItem(@RequestBody CartItem cartItem,
            Cart cart,
            Principal principal){
        String userName = principal.getName();
        Optional<Cart>optionalCart = cartRespository.findById(cart.getId());
        cartItemRespository.save(cartItem);
        return ResponseEntity.ok("ok");
    }
}
