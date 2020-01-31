package rs.ac.uns.ftn.sep.bank.exception;

import lombok.Getter;

@Getter
public class UnknownMerchant extends RuntimeException {
    private final Long paymentId;
    private final String merchantUsername;

    public UnknownMerchant(Long paymentId, String merchantUsername) {
        super("Unknown merchant: " + merchantUsername);
        this.paymentId = paymentId;
        this.merchantUsername = merchantUsername;
    }
}
