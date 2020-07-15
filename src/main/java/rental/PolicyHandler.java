package rental;



import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import rental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class PolicyHandler{
    @Autowired NotifyRepository notifyRepository;
    private static final String TOPIC_NAME = "rental";
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    Message message = new Message();


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_Notify(@Payload Ordered ordered){



        if(ordered.isMe()){
            message.setMessage( "주문이 완료되었습니다. 주문번호 : " + ordered.getId() + ", 상품번호 : " + ordered.getProductId() );
            notifyRepository.save(message);

        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverChecked_Notify(@Payload Checked checked) {



        if(checked.isMe()){
            message.setMessage("점검이 완료되었습니다. 주문번호 : " + checked.getOrderId() + ", 점검일자: " + checked.getCheckDate());
            notifyRepository.save(message);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverScheduleFixed_Notify(@Payload ScheduleFixed scheduleFixed){

        if(scheduleFixed.isMe()){
            message.setMessage("점검일정이 확정되었습니다. 주문번호 : " + scheduleFixed.getOrderId() + ", 점검일자: " + scheduleFixed.getCheckDate());
            notifyRepository.save(message);
            //System.out.println("Kakao : 점검일정이 확정되었습니다. 주문번호 : " + scheduleFixed.getOrderId() + ", 점검일자 : " + scheduleFixed.getCheckDate());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_Notify(@Payload Paid paid){

        if(paid.isMe()){
            message.setMessage("결제가 완료되었습니다. 주문번호 : " + paid.getOrderId() + ", 결제금액: " + paid.getRentalPrice());
            notifyRepository.save(message);
            //System.out.println("Kakao : 결제가 완료되었습니다. 주문번호 : " + paid.getOrderId() + ", 결제금액 : " + paid.getRentalPrice());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCanceled_Notify(@Payload PayCanceled payCanceled){

        if(payCanceled.isMe()){
            message.setMessage("결제가 취소되었습니다. 주문번호 : " + payCanceled.getOrderId());
            notifyRepository.save(message);
            //System.out.println("Kakao : 결제가 취소되었습니다. 주문번호 : " + payCanceled.getOrderId());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDelivered_Notify(@Payload Delivered delivered){

        if(delivered.isMe()){
            message.setMessage("배송이 완료되었습니다. 주문번호 : " + delivered.getOrderId() );
            notifyRepository.save(message);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_Notify(@Payload OrderCanceled orderCanceled){

        if(orderCanceled.isMe()){
            message.setMessage("주문이 취소되었습니다. 주문번호 : " + orderCanceled.getId() + ", 상품번호: " + orderCanceled.getProductId());
            notifyRepository.save(message);
            //System.out.println("Kakao : 주문이 취소되었습니다. 주문번호 : " + orderCanceled.getId() + ", 상품번호 : " + orderCanceled.getProductId());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCanceled_Notify(@Payload DeliveryCanceled deliveryCanceled){

        if(deliveryCanceled.isMe()){
            message.setMessage("배송이 취소되었습니다. 주문번호 : " + deliveryCanceled.getOrderId() );
            notifyRepository.save(message);
            //System.out.println("Kakao : 배송이 취소되었습니다. 주문번호 : " + deliveryCanceled.getOrderId());
        }
    }

}
