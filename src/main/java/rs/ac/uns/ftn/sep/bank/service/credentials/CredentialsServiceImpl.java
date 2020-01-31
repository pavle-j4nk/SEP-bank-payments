package rs.ac.uns.ftn.sep.bank.service.credentials;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.sep.bank.bom.Credentials;
import rs.ac.uns.ftn.sep.bank.repository.CredentialsRepository;
import rs.ac.uns.ftn.sep.commons.client.SellerClient;
import rs.ac.uns.ftn.sep.commons.dto.seller.CreatePaymentMethodDto;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {
    private final CredentialsRepository credentialsRepository;
    private final SellerClient sellerClient;

    @Override
    public Credentials addCredentials(String userEmail, Credentials credentials) {
        credentials.setUsername(userEmail);
        credentials = credentialsRepository.save(credentials);

        CreatePaymentMethodDto createPaymentMethodDto = CreatePaymentMethodDto.builder()
                .email(credentials.getUsername())
                .externalId(credentials.getId())
                .build();
        sellerClient.createPaymentMethod(createPaymentMethodDto);

        return credentials;
    }

    @Override
    public Optional<Credentials> getByMerchant(String username) {
        return credentialsRepository.getOneByUsername(username);
    }

}
