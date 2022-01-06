/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.security;

import co.amri.tokoshop.tokoshop.entity.User;
import co.amri.tokoshop.tokoshop.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author LENOVO
 */
@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByName(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getName(), user.getPassword(), Collections.emptyList()
            );
        } else {
            throw new UsernameNotFoundException("User not Found With Username : "
                    + username);
        }
        
    }
}
