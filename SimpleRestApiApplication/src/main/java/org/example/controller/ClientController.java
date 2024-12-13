package org.example.controller;

import jakarta.validation.Valid;
import org.example.exception.ResourceNotFoundException;
import org.example.service.ClientService;
import org.example.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@Valid @RequestBody Client client) {
        Client clientAdded = clientService.saveClientInfo(client);

        return ResponseEntity.ok(clientAdded);
    }

    @PostMapping("/add/list")
    public ResponseEntity<List<Client>> addClientList(@Valid @RequestBody List<Client> clients) {
        List<Client> clientsAdded = clientService.saveClientInfoList(clients);

        return ResponseEntity.ok(clientsAdded);
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {

        clientService.findByClientId(id) .orElseThrow(() -> new ResourceNotFoundException("Client Id " + id + " is not exist"));
        clientService.deleteByClientId(id);

        return "Client id [" + id + "] deleted successfully!";
    }

    @GetMapping("/find")
    public Optional<Client> getClient(@RequestParam Long id) {
        return clientService.findByClientId(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClientInfo(@PathVariable Long id, @Valid @RequestBody Client client) {

        Client existedClient = clientService.findByClientId(id) .orElseThrow(() -> new ResourceNotFoundException("Client Id " + id + " is not exist"));

        existedClient.setName(client.getName());
        existedClient.setPhoneNo(client.getPhoneNo());
        existedClient.setEmail(client.getEmail());

        clientService.saveClientInfo(existedClient);

        return ResponseEntity.ok(existedClient);
    }

    @GetMapping("/findWithPagination")
    public Page<Client> getClientsWithPagination(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return clientService.getClientInfoWithPagination(pageNum, pageSize);
    }

}
