/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.controller;

import co.amri.tokoshop.tokoshop.TokoshopApplication;
import co.amri.tokoshop.tokoshop.entity.Cart;
import co.amri.tokoshop.tokoshop.entity.CartItem;
import co.amri.tokoshop.tokoshop.entity.User;
import co.amri.tokoshop.tokoshop.repository.CartItemRespository;
import co.amri.tokoshop.tokoshop.repository.CartProduserRepository;
import co.amri.tokoshop.tokoshop.repository.CartRespository;
import co.amri.tokoshop.tokoshop.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping("/api")
public class CheckoutController_Produser {

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_PROCESSED = "PROCESSED";

    @Autowired
    public CartProduserRepository cartProduserRespository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public CartItemRespository cartItemRespository;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(Principal principal) {
        String userName = principal.getName();
        User user = userRepository.getUserByName(userName);
        //get cart status active

        Cart activeCart = cartProduserRespository.findCartByStatusAndUser(STATUS_ACTIVE, user);
        if (activeCart != null) {
            activeCart.setStatus(STATUS_PROCESSED);
            cartProduserRespository.save(activeCart);

            //send message to RabbitMQ
            String data = activeCart.getStatus();
            Optional<Cart> optionalCart = cartProduserRespository.findById(activeCart.getId());
            List<Cart> carts = cartProduserRespository.findCartByUserAndStatus(user, STATUS_PROCESSED);
            for (Cart cart : carts) {

            }
            try {
                String JsonStr = objectMapper.writeValueAsString(carts);
                System.out.println(JsonStr);

                amqpTemplate.convertAndSend(
                        TokoshopApplication.TOPIC_EXCHANGE_NAME,
                        "tokoshop.checkout",
                        "sending cart data with id: " + JsonStr);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.badRequest().body("no active cart for this user");
    }

}
