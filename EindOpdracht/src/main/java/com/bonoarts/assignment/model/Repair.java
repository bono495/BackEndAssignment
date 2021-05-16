package com.bonoarts.assignment.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "repairs")
public class Repair extends AuditModel{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Inspection
    //
    // Finished
    // Agreed
    // Denied
    @Column
    private String status;

    @Column(nullable = false)
    private boolean payed = false;

    @ManyToOne
    private Car car;

    @ManyToMany()
    @JoinTable(name = "repair_action",
            joinColumns = @JoinColumn(name = "repair_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id"))
    private List<Action> actions;

    @ManyToOne
    private User executed_by;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getExecuted_by() {
        return executed_by;
    }

    public void setExecuted_by(User executed_by) {
        this.executed_by = executed_by;
    }
}
