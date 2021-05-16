package com.bonoarts.assignment.service;

import com.bonoarts.assignment.model.Resource;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ResourceService {

    public abstract Integer createResource(Resource Resource);
    public abstract void updateResource(Integer id, Resource Resource);
    public abstract void partialUpdateResource(Integer id, Map<String, String> fields);
    public abstract void deleteResource(Integer id);
    public abstract Collection<Resource> getResources();
    public abstract Optional<Resource> getResource(Integer id);
    public abstract boolean resourceExists(Integer id);
}