package rs.ac.uns.ftn.sep.bank.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.sep.commons.dto.ExternalBankPaymentRequest;
import rs.ac.uns.ftn.sep.commons.dto.ExternalBankPaymentResponse;
import rs.ac.uns.ftn.sep.commons.enums.Service;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ExternalBankImpl implements ExternalBank {
    public static final String PAYMENT_PATH = "/payment";
    private final LoadBalancerClient loadBalancer;
    private final RestTemplate restTemplate;

    @Override
    public ExternalBankPaymentResponse createPayment(ExternalBankPaymentRequest request) {
        URI uri = loadBalancer.choose(Service.EXTERNAL_BANK.name()).getUri();
        String paymentUrl = uri.toString() + PAYMENT_PATH;

        return restTemplate.postForEntity(paymentUrl, request, ExternalBankPaymentResponse.class).getBody();
    }
}
