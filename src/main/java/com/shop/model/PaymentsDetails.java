package com.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Getter
@Setter
public class PaymentsDetails {
    @NotBlank(message = "Card number cannot be blank")
    @Pattern(regexp = "[0-9]{4}\\ [0-9]{4}\\ [0-9]{4}\\ +[0-9]{4}",
            message = "Card num should be like '0000 0000 0000 0000'")
    private String cardNum;
    @NotBlank(message = "CVV cannot be blank")
    @Pattern(regexp = "[0-9]{3}", message = "CVV should contains 3 numbers")
    private String cvv;
    @NotBlank(message = "Date cannot be blank")
    @Pattern(regexp = "[0-9]{2}\\/[0-9]{2}", message = "Date should be like '00/00'")
    private String date;

    public PaymentsDetails() {
    }


    public PaymentsDetails(String cardNum, String cvv, String date) {
        this.cardNum = cardNum;
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentsDetails that = (PaymentsDetails) o;
        return Objects.equals(cardNum, that.cardNum) && Objects.equals(cvv, that.cvv)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNum, cvv, date);
    }
}
