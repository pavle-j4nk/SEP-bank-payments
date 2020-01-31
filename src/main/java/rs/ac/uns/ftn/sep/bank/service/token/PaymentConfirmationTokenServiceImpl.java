package rs.ac.uns.ftn.sep.bank.service.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.sep.bank.bom.Payment;
import rs.ac.uns.ftn.sep.bank.bom.PaymentConfirmationToken;
import rs.ac.uns.ftn.sep.bank.repository.PaymentConfirmationTokenRepository;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatus;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentConfirmationTokenServiceImpl implements PaymentConfirmationTokenService {
    private final TokenGenerator tokenGenerator;
    private final PaymentConfirmationTokenRepository repository;

    @Override
    public PaymentConfirmationToken createToken(Payment payment, PaymentStatus status) {
        String token = tokenGenerator.generateToken();
        PaymentConfirmationToken tokenEntity = PaymentConfirmationToken.builder()
                .payment(payment).status(status).token(token).build();

        return repository.save(tokenEntity);
    }

    @Override
    public PaymentConfirmationToken findOneByValue(String value) {
        return repository.getOneByToken(value);
    }

}
