package rs.ac.uns.ftn.sep.bank.service.credentials;

import rs.ac.uns.ftn.sep.bank.bom.Credentials;

import java.util.Optional;

public interface CredentialsService {

    Credentials addCredentials(String userEmail, Credentials credentials);

    Optional<Credentials> getByMerchant(String username);

}
