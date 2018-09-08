package com.vankata.residentevil.domain.entity;

import com.vankata.residentevil.domain.enums.VirusCreator;
import com.vankata.residentevil.domain.enums.VirusMagnitude;
import com.vankata.residentevil.domain.enums.VirusMutation;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "viruses")
public class Virus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "side_effects")
    private String sideEffects;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VirusCreator creator;

    @Column(name = "is_deadly")
    private boolean isDeadly;

    @Column(name = "is_curable")
    private boolean isCurable;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VirusMutation mutation;

    @Column(name = "turnover_rate")
    private int turnoverRate;

    @Column(name = "hours_until_turn")
    private int hoursUntilTurn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VirusMagnitude magnitude;

    @Column(name = "released_on", nullable = false)
    private LocalDate releasedOn;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "viruses_capitals", joinColumns = @JoinColumn(name = "virus_id"),
            inverseJoinColumns = @JoinColumn(name = "capital_id"))
    private List<Capital> capitals;

    public Virus() {
        this.capitals = new ArrayList<>();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSideEffects() {
        return this.sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public VirusCreator getCreator() {
        return this.creator;
    }

    public void setCreator(VirusCreator creator) {
        this.creator = creator;
    }

    public boolean getIsDeadly() {
        return this.isDeadly;
    }

    public void setIsDeadly(boolean deadly) {
        this.isDeadly = deadly;
    }

    public boolean getIsCurable() {
        return this.isCurable;
    }

    public void setIsCurable(boolean curable) {
        this.isCurable = curable;
    }

    public VirusMutation getMutation() {
        return this.mutation;
    }

    public void setMutation(VirusMutation mutation) {
        this.mutation = mutation;
    }

    public int getTurnoverRate() {
        return this.turnoverRate;
    }

    public void setTurnoverRate(int turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public int getHoursUntilTurn() {
        return this.hoursUntilTurn;
    }

    public void setHoursUntilTurn(int hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    public VirusMagnitude getMagnitude() {
        return this.magnitude;
    }

    public void setMagnitude(VirusMagnitude magnitude) {
        this.magnitude = magnitude;
    }

    public LocalDate getReleasedOn() {
        return this.releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    public List<Capital> getCapitals() {
        return this.capitals;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }
}
