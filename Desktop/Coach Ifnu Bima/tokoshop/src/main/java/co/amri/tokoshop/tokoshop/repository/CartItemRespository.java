/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.repository;

import co.amri.tokoshop.tokoshop.entity.Cart;
import co.amri.tokoshop.tokoshop.entity.CartItem;
import co.amri.tokoshop.tokoshop.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author LENOVO
 */
public interface CartItemRespository extends CrudRepository<CartItem, Integer>{
    public Optional<CartItem> findByProduct(Product product);
    public List<CartItem>findCartItemByCart(Cart cart);
}
