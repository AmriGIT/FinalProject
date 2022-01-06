/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private JwtUserDetailService userDetailService;
    
    @PostMapping("/authenticate")
    public  ResponseEntity<?> createAuthenticationToken(
    @RequestBody JwtRequest authenticationRequest)throws Exception{
        authentiacte(authenticationRequest.getUsername(), 
                authenticationRequest.getPassword());
        UserDetails userDetails = 
                userDetailService.loadUserByUsername(
                authenticationRequest.getUsername()
                );
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    
    private void authentiacte(String username, String password) throws Exception{
        try {
                authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (DisabledException ex) {
            throw new Exception("USER_DISABLED", ex);
        }catch(BadCredentialsException ex){
            throw new Exception("INVALID_CREDENTIALS", ex);
        }
    }
    
}
