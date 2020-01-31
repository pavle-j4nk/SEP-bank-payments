package rs.ac.uns.ftn.sep.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.sep.bank.bom.PaymentConfirmationToken;

public interface PaymentConfirmationTokenRepository extends JpaRepository<PaymentConfirmationToken, Long> {

    PaymentConfirmationToken getOneByToken(String token);

}
