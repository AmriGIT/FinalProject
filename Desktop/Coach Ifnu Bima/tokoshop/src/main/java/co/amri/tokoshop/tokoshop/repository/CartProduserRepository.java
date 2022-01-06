/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.repository;

import co.amri.tokoshop.tokoshop.entity.Cart;
import co.amri.tokoshop.tokoshop.entity.User;
import java.util.List;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author LENOVO
 */
public interface CartProduserRepository extends CrudRepository<Cart, Integer>{
   
    public Cart findCartByStatusAndUser(String status, User user);
    
    public List<Cart> findCartByUserAndStatus(User user, String status);
    
}

