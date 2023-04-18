package com.shop.util;

import com.shop.model.PaymentsDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsPool {
    private String cardNum;
    private String cvv;
    private String date;

    public boolean isPaymentDetailsInPool(PaymentsDetails paymentsDetails) {
        return paymentsDetails.getCardNum().equals(cardNum) && paymentsDetails.getCvv().equals(cvv)
                && paymentsDetails.getDate().equals(date);
    }
}
