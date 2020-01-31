package rs.ac.uns.ftn.sep.bank.service.payment;

import rs.ac.uns.ftn.sep.bank.bom.Payment;
import rs.ac.uns.ftn.sep.commons.service.BasePaymentService;

public interface PaymentService extends BasePaymentService {

    Payment completePayment(String confirmationToken);

}
