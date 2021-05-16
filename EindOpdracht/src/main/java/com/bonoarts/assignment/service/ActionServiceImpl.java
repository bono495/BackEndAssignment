package com.bonoarts.assignment.service;

import com.bonoarts.assignment.exceptions.RecordNotFoundException;
import com.bonoarts.assignment.model.Action;
import com.bonoarts.assignment.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Collection<Action> getActions() {
        return actionRepository.findAll();
    }

    @Override
    public Optional<Action> getAction(Integer id) {
        return actionRepository.findById(id);
    }

    @Override
    public boolean actionExists(Integer id) {
        return actionRepository.existsById(id);
    }

    @Override
    public Integer createAction(Action action) {
        Action newAction = actionRepository.save(action);
        return newAction.getId();
    }

    @Override
    public void deleteAction(Integer id) {
        actionRepository.deleteById(id);
    }

    @Override
    public void updateAction(Integer id, Action newAction) {
        if (!actionRepository.existsById(id)) throw new RecordNotFoundException();
        Action action = actionRepository.findById(id).get();

        action.setName(newAction.getName());
        action.setPrice(newAction.getPrice());

        actionRepository.save(action);
    }

    @Override
    public void partialUpdateAction(Integer id, Map<String, String> fields) {
        if (!actionRepository.existsById(id)) throw new RecordNotFoundException();
        Action action = actionRepository.findById(id).get();

        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "name":
                    action.setName((String) fields.get(field));
                    break;
                case "price":
                    action.setPrice((Integer) parseInt(fields.get(field)));
                    break;
                case "duration":
                    action.setDuration((Integer) parseInt(fields.get(field)));
                    break;
            }
        }
        actionRepository.save(action);
    }
}