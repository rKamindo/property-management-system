package com.randy.propertymanagementsystem.ownership;

import com.randy.propertymanagementsystem.client.ClientService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnershipService {
    private final ClientService clientService;

    public boolean belongsToUser(IClientResource clientResource) {
        return clientResource.getClient()
                .equals(clientService.getCurrentClient());
    }
}
