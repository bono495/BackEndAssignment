package com.bonoarts.assignment.service;

import com.bonoarts.assignment.exceptions.RecordNotFoundException;
import com.bonoarts.assignment.model.Client;
import com.bonoarts.assignment.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientsRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Collection<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClient(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public boolean clientExists(Integer id) {
        return clientRepository.existsById(id);
    }

    @Override
    public Integer createClient(Client client) {
        Client newClient = clientRepository.save(client);
        return newClient.getId();
    }

    @Override
    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void updateClient(Integer id, Client newClient) {
        if (!clientRepository.existsById(id)) throw new RecordNotFoundException();
        Client client = clientRepository.findById(id).get();

        client.setFirst_name(newClient.getFirst_name());
        client.setLast_name(newClient.getLast_name());
        client.setEmail(newClient.getEmail());
        client.setAddress(newClient.getAddress());
        client.setPhonenumber(newClient.getPhonenumber());

        clientRepository.save(client);
    }

    @Override
    public void partialUpdateClient(Integer id, Map<String, String> fields) {
        if (!clientRepository.existsById(id)) throw new RecordNotFoundException();
        Client client = clientRepository.findById(id).get();

        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "first_name":
                    client.setFirst_name((String) fields.get(field));
                    break;
                case "last_name":
                    client.setLast_name((String) fields.get(field));
                    break;
                case "email":
                    client.setEmail((String) fields.get(field));
                    break;
                case "address":
                    client.setAddress((String) fields.get(field));
                    break;
                case "phonenumber":
                    client.setPhonenumber((String) fields.get(field));
                    break;
            }
        }
        clientRepository.save(client);
    }
}