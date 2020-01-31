package rs.ac.uns.ftn.sep.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.sep.bank.bom.Credentials;
import rs.ac.uns.ftn.sep.bank.service.credentials.CredentialsService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://localhost", "https://dev.local"})
@RequestMapping("/api/credentials")
public class CredentialsApiController {
    private final CredentialsService credentialsService;

    @PostMapping
    @PreAuthorize("hasRole('MERCHANT')")
    public Credentials addCredentials(Principal principal, @RequestBody Credentials credentials) {
        credentials.setUsername(principal.getName());
        return credentialsService.addCredentials(principal.getName(), credentials);
    }

}
