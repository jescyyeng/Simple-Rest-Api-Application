package org.example.service;

import jakarta.transaction.Transactional;
import org.example.repository.ClientRepository;
import org.example.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client saveClientInfo(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public List<Client> saveClientInfoList(List<Client> clients) {
        return clientRepository.saveAll(clients);
    }

    @Transactional
    public void deleteByClientId(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public Optional<Client> findByClientId(Long id) {
        return clientRepository.findById(id);
    }


    @Transactional
    public Page<Client> getClientInfoWithPagination(int pageNum, int pageSize) {
        return clientRepository.findAll(PageRequest.of(pageNum, pageSize));
    }

}
