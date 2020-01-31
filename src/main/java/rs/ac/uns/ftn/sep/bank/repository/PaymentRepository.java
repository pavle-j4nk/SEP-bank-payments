package rs.ac.uns.ftn.sep.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.sep.bank.bom.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
