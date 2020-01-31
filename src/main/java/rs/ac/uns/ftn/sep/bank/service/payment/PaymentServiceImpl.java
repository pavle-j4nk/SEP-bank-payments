package rs.ac.uns.ftn.sep.bank.service.payment;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.sep.bank.bom.Credentials;
import rs.ac.uns.ftn.sep.bank.bom.Payment;
import rs.ac.uns.ftn.sep.bank.bom.PaymentConfirmationToken;
import rs.ac.uns.ftn.sep.bank.exception.PaymentNotFound;
import rs.ac.uns.ftn.sep.bank.exception.UnknownMerchant;
import rs.ac.uns.ftn.sep.bank.repository.PaymentRepository;
import rs.ac.uns.ftn.sep.bank.service.credentials.CredentialsService;
import rs.ac.uns.ftn.sep.bank.service.token.PaymentConfirmationTokenService;
import rs.ac.uns.ftn.sep.commons.dto.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private static final String URL_PAYMENT_COMPLETED = "payment?token=";

    private final PaymentRepository paymentRepository;
    private final CredentialsService credentialsService;
    private final PaymentConfirmationTokenService confirmationTokenService;
    private final ExternalBank externalBank;

    @Value("${root-url}")
    private String rootUrl;

    @Override
    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {
        LOGGER.debug("Create payment request received [merchant=%s, amount=%s]", request.getMerchantName(), request.getAmount().toString());
        Payment payment = createLocalPayment(request);

        ExternalBankPaymentRequest externalBankPaymentRequest = createExternalBankPaymentRequest(payment);
        ExternalBankPaymentResponse externalBankPaymentResponse = externalBank.createPayment(externalBankPaymentRequest);

        CreatePaymentResponse response = CreatePaymentResponse.builder().paymentId(payment.getId())
                .redirect(externalBankPaymentResponse.getUrl()).build();
        return response;
    }

    /**
     * Creates and persists local {@link Payment} instance.
     *
     * @param request
     * @return
     */
    private Payment createLocalPayment(CreatePaymentRequest request) {
        Payment payment = initializePayment(request);
        payment = assignCredentials(payment, request.getMerchantName());
        return payment;
    }

    private ExternalBankPaymentRequest createExternalBankPaymentRequest(Payment payment) {
        String successUrl = assignTokenAndGetUrl(payment, PaymentStatus.SUCCESS);
        String failedUrl = assignTokenAndGetUrl(payment, PaymentStatus.FAIL);
        String errorUrl = assignTokenAndGetUrl(payment, PaymentStatus.ERROR);

        ExternalBankPaymentRequest paymentRequest = new ExternalBankPaymentRequest(payment.getMerchant().getMerchantId()
                , payment.getMerchant().getMerchantPassword(), new BigDecimal(payment.getAmount())
                , payment.getId().intValue(), Date.valueOf(payment.getDateTimeCreated().toLocalDate())
                , successUrl, failedUrl, errorUrl);

        return paymentRequest;
    }

    private Payment initializePayment(CreatePaymentRequest request) {
        Payment payment = Payment.builder().requestedMerchant(request.getMerchantName()).redirectUrl(request.getRedirectUrl())
                .amount(request.getAmount()).status(PaymentStatus.PENDING).dateTimeCreated(LocalDateTime.now()).build();

        return paymentRepository.save(payment);
    }

    private Payment assignCredentials(Payment payment, String merchantUsername) {
        Credentials credentials = credentialsService.getByMerchant(merchantUsername).orElseThrow(() -> new UnknownMerchant(payment.getId(), merchantUsername));
        payment.setMerchant(credentials);
        return paymentRepository.save(payment);
    }

    private String assignTokenAndGetUrl(Payment payment, PaymentStatus status) {
        PaymentConfirmationToken token = confirmationTokenService.createToken(payment, status);
        return rootUrl + URL_PAYMENT_COMPLETED + token.getToken();
    }

    @Override
    public Payment completePayment(String confirmationToken) {
        PaymentConfirmationToken token = confirmationTokenService.findOneByValue(confirmationToken);
        Payment payment = token.getPayment();
        payment.setStatus(token.getStatus());
        payment.setDateTimeCompleted(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    @Override
    public PaymentStatusResponse getPaymentStatus(PaymentStatusRequest request) {
        Optional<Payment> optionalPayment = paymentRepository.findById(request.getPaymentId());
        Payment payment = optionalPayment.orElseThrow(PaymentNotFound::new);

        return new PaymentStatusResponse(payment.getId(), payment.getStatus());
    }
}
