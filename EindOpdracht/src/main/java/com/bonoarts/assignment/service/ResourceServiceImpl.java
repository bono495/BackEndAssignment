package com.bonoarts.assignment.service;

import com.bonoarts.assignment.exceptions.RecordNotFoundException;
import com.bonoarts.assignment.model.Resource;
import com.bonoarts.assignment.repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourcesRepository resourceRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Collection<Resource> getResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Optional<Resource> getResource(Integer id) {
        return resourceRepository.findById(id);
    }

    @Override
    public boolean resourceExists(Integer id) {
        return resourceRepository.existsById(id);
    }

    @Override
    public Integer createResource(Resource resource) {
        Resource newResource = resourceRepository.save(resource);
        return newResource.getId();
    }

    @Override
    public void deleteResource(Integer id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public void updateResource(Integer id, Resource newResource) {
        if (!resourceRepository.existsById(id)) throw new RecordNotFoundException();
        Resource resource = resourceRepository.findById(id).get();

        resource.setName(newResource.getName());
        resource.setPrice(newResource.getPrice());
        resource.setStock(newResource.getStock());

        resourceRepository.save(resource);
    }

    @Override
    public void partialUpdateResource(Integer id, Map<String, String> fields) {
        if (!resourceRepository.existsById(id)) throw new RecordNotFoundException();
        Resource resource = resourceRepository.findById(id).get();

        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "name":
                    resource.setName((String) fields.get(field));
                    break;
                case "price":
                    resource.setPrice((Integer) parseInt(fields.get(field)));
                    break;
                case "stock":
                    resource.setStock((Integer) parseInt(fields.get(field)));
                    break;
            }
        }
        resourceRepository.save(resource);
    }
}