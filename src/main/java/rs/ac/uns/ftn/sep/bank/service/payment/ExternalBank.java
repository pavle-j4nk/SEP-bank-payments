package rs.ac.uns.ftn.sep.bank.service.payment;

import rs.ac.uns.ftn.sep.commons.dto.ExternalBankPaymentRequest;
import rs.ac.uns.ftn.sep.commons.dto.ExternalBankPaymentResponse;

public interface ExternalBank {

    ExternalBankPaymentResponse createPayment(ExternalBankPaymentRequest request);

}
