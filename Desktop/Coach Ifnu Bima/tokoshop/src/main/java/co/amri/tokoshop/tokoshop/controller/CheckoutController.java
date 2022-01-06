///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package co.amri.tokoshop.tokoshop.controller;
//
//import co.amri.tokoshop.tokoshop.TokoshopApplication;
//import co.amri.tokoshop.tokoshop.entity.Cart;
//import co.amri.tokoshop.tokoshop.entity.User;
//import co.amri.tokoshop.tokoshop.repository.CartRespository;
//import co.amri.tokoshop.tokoshop.repository.UserRepository;
//import java.security.Principal;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// *
// * @author LENOVO
// */
//@RestController
//@RequestMapping("/api")
//public class CheckoutController {
//
//    public static final String STATUS_ACTIVE = "ACTIVE";
//    public static final String STATUS_PROCESSED = "PROCESSED";
//
//    @Autowired
//    public CartRespository cartRespository;
//    @Autowired
//    public UserRepository userRepository;
//    @Autowired
//    public RabbitTemplate rabbitTemplate;
//
//    @PostMapping("/checkout")
//    public ResponseEntity<String> checkout(Principal principal) {
//        String userName = principal.getName();
//        User user = userRepository.getUserByName(userName);
//        //get cart status active
//
//        Cart activeCart = cartRespository.findCartByStatusAndUser(STATUS_ACTIVE, user);
//        if (activeCart != null) {
//            activeCart.setStatus(STATUS_PROCESSED);
//            cartRespository.save(activeCart);
//
//            //send message to RabbitMQ
//            rabbitTemplate.convertAndSend(
//                    TokoshopApplication.TOPIC_EXCHANGE_NAME,
//                    "tokoshop.checkout",
//                    "sending cart data with id: " + activeCart.getId());
//            return ResponseEntity.ok("ok");
//        }
//        return ResponseEntity.badRequest().body("no active cart for this user");
//    }
//
//}
