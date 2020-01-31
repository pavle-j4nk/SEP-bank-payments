package rs.ac.uns.ftn.sep.bank.service.token;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenGeneratorImpl implements TokenGenerator {

    @Override
    public String generateToken() {
        return RandomStringUtils.random(32, true, true);
    }

}
