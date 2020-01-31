package rs.ac.uns.ftn.sep.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.sep.bank.bom.Credentials;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    Optional<Credentials> getOneByUsername(String username);

}
