package rs.ac.uns.ftn.sep.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.sep.commons.client.SellerClient;
import rs.ac.uns.ftn.sep.commons.dto.seller.CreatePaymentMethodDto;
import rs.ac.uns.ftn.sep.commons.dto.seller.SupportedPaymentMethodDto;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test2WaySslController {
    private final SellerClient sellerClient;

    @GetMapping
    public SupportedPaymentMethodDto test() {
        CreatePaymentMethodDto request = new CreatePaymentMethodDto();
        request.setEmail("test@mail.com");
        request.setExternalId(231L);
        request.setName("First method");
        return sellerClient.createPaymentMethod(request);
    }

}
