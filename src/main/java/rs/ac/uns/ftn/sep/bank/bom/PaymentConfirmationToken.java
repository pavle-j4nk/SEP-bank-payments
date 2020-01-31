package rs.ac.uns.ftn.sep.bank.bom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmationToken {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private PaymentStatus status;
    @ManyToOne
    private Payment payment;
}
