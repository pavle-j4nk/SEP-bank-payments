package rs.ac.uns.ftn.sep.bank.bom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeCompleted;
    private String requestedMerchant;
    @ManyToOne
    private Credentials merchant;
    private String redirectUrl;

    @OneToMany(mappedBy = "payment")
    @JsonIgnore
    private List<PaymentConfirmationToken> confirmationTokens;
}
