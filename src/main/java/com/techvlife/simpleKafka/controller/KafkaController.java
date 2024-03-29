package com.techvlife.simpleKafka.controller;

import com.techvlife.simpleKafka.pojo.KafkaConsumerRequest;
import com.techvlife.simpleKafka.pojo.KafkaMessage;
import com.techvlife.simpleKafka.pojo.KafkaProducerResponse;
import com.techvlife.simpleKafka.service.consumer.SimpleConsumer;
import com.techvlife.simpleKafka.service.producer.ProducerWithCallback;
import com.techvlife.simpleKafka.service.producer.ProducerWithKey;
import com.techvlife.simpleKafka.service.producer.SimpleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private SimpleProducer simpleProducer;
    @Autowired
    private ProducerWithCallback producerWithCallback;
    @Autowired
    private ProducerWithKey producerWithKey;
    @Autowired
    private SimpleConsumer simpleConsumer;

    @RequestMapping(value = "/producer/simple", method = RequestMethod.POST)
    public String push_messages(@RequestBody  KafkaMessage message){

        boolean success = simpleProducer.push_data(message.getData());

        if(success){
            return "Data submitted successfully";
        }else{
            return "Error in saving data to Kafka";
        }
    }

    @RequestMapping(value = "/producer/callback", method = RequestMethod.POST)
    public KafkaProducerResponse push_messages_callback(@RequestBody  KafkaMessage message){
        return producerWithCallback.push_data(message.getData());
    }

    @RequestMapping(value = "/producer/key", method = RequestMethod.POST)
    public KafkaProducerResponse push_messages_key(@RequestBody  KafkaMessage message){
        return producerWithKey.push_data(message);
    }

    @RequestMapping(value = "/consumer/simple", method = RequestMethod.POST)
    public void consume_messages(@RequestBody KafkaConsumerRequest request){
        simpleConsumer.consume_messages(request);
    }


}
