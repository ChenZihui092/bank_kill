package com.example.bank_kill.config;


import com.example.bank_kill.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean(name = "EX_DIRECT")
    public DirectExchange directExchange(){
        return new DirectExchange(MqConstant.EX_DIRECT);
    }

    @Bean(name = "KILL_SUCCESS_QUEUE")
    public Queue directQueue(){
        return new Queue(MqConstant.KILL_SUCCESS_QUEUE);
    }

    @Bean
    public Binding bindPeakQueue(@Qualifier("KILL_SUCCESS_QUEUE") Queue queue,@Qualifier("EX_DIRECT") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("kill.success");
    }

    /**
     * 接收购买失败的用户消息
     * @return
     */
    @Bean(name = "KILL_FILL_QUEUE")
    public Queue killFillQueue(){return new Queue(MqConstant.KILL_FILL_QUEUE);}

    @Bean
    public Binding bindFillQueue(@Qualifier("KILL_FILL_QUEUE") Queue queue,@Qualifier("EX_DIRECT") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("kill.fill");
    }


}
