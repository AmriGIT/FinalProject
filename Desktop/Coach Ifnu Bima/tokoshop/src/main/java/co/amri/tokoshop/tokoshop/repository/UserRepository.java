/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.repository;

import co.amri.tokoshop.tokoshop.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author LENOVO
 */
public interface UserRepository extends CrudRepository<User, Integer>{
 public User getUserByName(String userName);
}
