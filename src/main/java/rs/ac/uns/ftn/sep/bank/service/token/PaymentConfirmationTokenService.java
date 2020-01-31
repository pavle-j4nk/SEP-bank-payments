package rs.ac.uns.ftn.sep.bank.service.token;

import rs.ac.uns.ftn.sep.bank.bom.Payment;
import rs.ac.uns.ftn.sep.bank.bom.PaymentConfirmationToken;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatus;

public interface PaymentConfirmationTokenService {

    /**
     * Creates and persists unique payment confirmation token.
     * @param payment associated payment
     * @param status confirmation status
     * @return new token
     */
    PaymentConfirmationToken createToken(Payment payment, PaymentStatus status);

    PaymentConfirmationToken findOneByValue(String value);

}
