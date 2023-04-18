package com.shop.config;

import com.shop.util.PaymentDetailsPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mock-banking.properties")
@Profile("dev")
public class LocalBankingConfig {

    @Bean(name ="paymentDetailsPool")
    public PaymentDetailsPool mockPaymentDetailsPool(@Value(value = "${card.num}") String cardNum,
                                                     @Value(value = "${cvv}")
                                                     String cvv,
                                                     @Value(value = "${date}")
                                                     String date) {
        return new PaymentDetailsPool(cardNum, cvv, date);
    }


}