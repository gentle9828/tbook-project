package com.example.itemservice.messagequeue;

import com.example.itemservice.ProductRepository;
import com.example.itemservice.config.OutOfStockException;
import com.example.itemservice.dto.ProductCartResponse;
import com.example.itemservice.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException; //jackson
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ProductRepository productRepository;
    private final KafkaTemplate kafkaTemplate;

    @KafkaListener(topics="update-quantity-product")
    public void updateQuantity(String kafkaMessage){
        log.info("Kafka Message: "+ kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try{
            //String값으로 전달받은 메시지를 우리가 원하는 데이터타입(Json)으로 변경해줍니다.
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        log.info("asdfasdf" + map.get("productId"));

        Long productId = Long.parseLong((String) map.get("productId"));
        Product product = productRepository.findById(productId).get();
        if(product != null){
            // 해당 상품이 있디면, 상품 재고를 갱신해주고, 저장시킵니다.
            product.setStock(product.getStock() - (Integer) map.get("stock"));
            productRepository.save(product);
        }

    }

    @KafkaListener(topics="retrieve-product")
    public void getRetrieveProduct(String kafkaMessage){
        log.info("Kafka Message: "+ kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try{
            //String값으로 전달받은 메시지를 우리가 원하는 데이터타입(Json)으로 변경해줍니다.
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        log.info("asdfasdf" + map.get("productId"));

        Long productId = Long.parseLong((String) map.get("productId"));
        Product product = productRepository.findById(productId).get();
        if(product != null){
            log.info(product.getId() + " " + product.getProductName());

            ProductCartResponse productCartResponse = ProductCartResponse.builder()
                    .userId(map.get("userId").toString())
                    .productId(product.getId().toString())
                    .productImage(product.getProductImage())
                    .productName(product.getProductName())
                    .productMadeBy(product.getProductMadeBy())
                    .productPrice(product.getProductPrice())
                    .build();

            try{
                String productJson = new ObjectMapper().writeValueAsString(productCartResponse);

                log.info("hello" +productJson);
                kafkaTemplate.send("cart-product-info", productJson);
            }catch(JsonProcessingException ex){
                log.error("Failed to Serialize to Product");
            }

//            kafkaTemplate.send("cart-product-info", product.toString());
//            return product;

            // 해당 상품이 있디면, 상품 재고를 갱신해주고, 저장시킵니다.
//            product.setStock(product.getStock() - (Integer) map.get("stock"));
//            productRepository.save(product);
        }
//        return product;

    }




//    private final ProductRepository productRepository;
//
////    //Kafka Product 의존성 주입
//    private final KafkaProducer kafkaProducer;
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    /**
//     * 사용자 주문에 대해 상품 재고가 남아있는지 확인
//     * @param kafkaMessage: 사용자 주문정보 (누가, 어떤상품을 원하는지)
//     */
//
//    @KafkaListener(topics="update-quantity-product")
//    public void updateQuantity(String kafkaMessage){
//        log.info("Kafka Message: {}", kafkaMessage);
//
//        Map<Object, Object> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//
//
//        try{
//            //String값으로 전달받은 메시지를 우리가 원하는 데이터타입(Json)으로 변경해줍니다.
//            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
//        } catch(JsonProcessingException ex){
//            ex.printStackTrace();
//        }
//
//        Product product = productRepository.findByProductId((String) map.get("productId"));
//        if(product != null){
//            // 상품 재고 확인
//            if(product.getStock() >= (Integer) map.get("stock")) {
//                // 상품 수량 감소, 저장
//                product.setStock(product.getStock() - (Integer) map.get("stock"));
////                productRepository.save(product);
//
//                // 주문 완료 알림
//                kafkaProducer.sendOrderConfirmation(map);
//            }
//            else {
//                // out of stock notification
//                try {
//                    // use KafkaTemplate to send messages
//                    kafkaTemplate.executeInTransaction(operations -> {
//                        try {
//                            operations.send("out-of-stock-notification-topic", kafkaMessage).get();
//                        } catch (InterruptedException e) {
//                            log.info("hi");
//                            throw new RuntimeException(e);
//                        } catch (ExecutionException e) {
//                            log.info("hello");
//                            throw new RuntimeException(e);
//                        }
//                        return true;
//                    });
//                } catch (Exception e) {
//                    log.error("Failed to send message to topic out-of-stock-notification-topic");
//                    throw new RuntimeException("Failed to send message to topic out-of-stock-notification-topic");
//                }
//                // handle exception for out-of-stock
//                throw new OutOfStockException("The product is out of stock.");
//            }
//
////            else {
////                // 재고 부족 알림
////                kafkaProducer.sendOutOfStockNotification(map);
////            }
//        }
//
//    }
//
//    /**
//     * 재고를 감소시킬 수 있는지 확인하는 메서드
//     * @param kafkaMessage
//     */
//
//    @KafkaListener(topics="update-try-quantity-product")
//    public void updateTryQuantity(String kafkaMessage){
//        log.info("Try: {}", kafkaMessage);
//
//        Map<Object, Object> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//
//
//        try{
//            //String값으로 전달받은 메시지를 우리가 원하는 데이터타입(Json)으로 변경해줍니다.
//            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
//        } catch(JsonProcessingException ex){
//            ex.printStackTrace();
//        }
//
//        Product product = productRepository.findByProductId((String) map.get("productId"));
//        if(product != null){
//            // 상품 재고 확인
//            if(product.getStock() >= (Integer) map.get("stock")){
//                // 상품 수량 감소, 저장
//                product.setStock(product.getStock() - (Integer) map.get("stock"));
////                productRepository.save(product);
//
//                // 주문 완료 알림
//                kafkaProducer.sendOrderConfirmation(map);
//            } else {
//                // 재고 부족 알림
//                kafkaProducer.sendOutOfStockNotification(map);
//            }
//        }
//
//    }
//

}
