package com.randy.propertymanagementsystem.client;

import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client findByEmail(String userEmail) {
        return clientRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Client not found by email: " + userEmail
                ));
    }
}
