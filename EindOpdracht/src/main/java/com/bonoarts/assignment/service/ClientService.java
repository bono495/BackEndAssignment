package com.bonoarts.assignment.service;

import com.bonoarts.assignment.model.Client;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ClientService {

    public abstract Integer createClient(Client Client);
    public abstract void updateClient(Integer id, Client Client);
    public abstract void partialUpdateClient(Integer id, Map<String, String> fields);
    public abstract void deleteClient(Integer id);
    public abstract Collection<Client> getClients();
    public abstract Optional<Client> getClient(Integer id);
    public abstract boolean clientExists(Integer id);
}