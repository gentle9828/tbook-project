//package com.example.itemservice.messagequeue;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class KafkaProducer {
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public Map<Object, Object> sendOutOfStockNotification(Map<Object, Object> orders) {
//        // 재고 부족 알림 메시지 생성
//        String message = "당신의 주문인 " + orders.get("productId") + "는 " +
//                "재고가 부족합니다.";
//
//        // 재고 부족 알림 메시지 큐(Kafka)에 전송
//        log.info(message);
//        kafkaTemplate.send("out-of-stock-notification-topic", message);
//
//        return null;
//    }
//
//    public Map<Object, Object> sendOrderConfirmation(Map<Object, Object> orders) {
//        ObjectMapper mapper = new ObjectMapper(); //json 형태로 변경하기 위해서 ObjectMapper를 사용하겠습니다.
//        String jsonInString = ""; //해당 string값의 모양새가 json형태로 표현되게끔 사용하겠습니다.
//
//        try {
//            jsonInString = mapper.writeValueAsString(orders);
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
////        String message = "당신의 주문인 " + orders.get("productId") + "의 " +
////                (Integer) orders.get("stock") + "건이 " + "정상적으로 처리되었습니다.";
//
//        kafkaTemplate.send("order-confirmation-topic", jsonInString);
//
//        log.info(jsonInString);
//        log.info("주문 micro service로부터 Kafka Producer를 통해 해당 데이터를 전송 완료하였습니다.");
//
//        return orders;
//    }
//}
