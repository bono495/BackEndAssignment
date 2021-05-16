package com.bonoarts.assignment.service;

import com.bonoarts.assignment.model.Action;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ActionService {

    public abstract Integer createAction(Action Action);
    public abstract void updateAction(Integer id, Action Action);
    public abstract void partialUpdateAction(Integer id, Map<String, String> fields);
    public abstract void deleteAction(Integer id);
    public abstract Collection<Action> getActions();
    public abstract Optional<Action> getAction(Integer id);
    public abstract boolean actionExists(Integer id);
}