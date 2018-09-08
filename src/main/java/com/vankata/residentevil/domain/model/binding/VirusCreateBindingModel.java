package com.vankata.residentevil.domain.model.binding;

import com.vankata.residentevil.domain.enums.VirusCreator;
import com.vankata.residentevil.domain.enums.VirusMagnitude;
import com.vankata.residentevil.domain.enums.VirusMutation;
import com.vankata.residentevil.validation.BeforeToday;
import com.vankata.residentevil.validation.NumberRange;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class VirusCreateBindingModel {

    private long id;

    @Size(min = 3, max = 10, message = "Invalid name! Should be between 3 and 10 characters!")
    private String name;

    @Size(min = 5, max = 100, message = "Invalid description! Should be between 5 and 100 characters!")
    private String description;

    @Size(max = 50, message = "Invalid side effects! Maximum 50 characters allowed!")
    private String sideEffects;

    @NotNull(message = "Invalid creator!")
    private VirusCreator creator;

    private boolean isDeadly;

    private boolean isCurable;

    @NotNull(message = "VirusMutation can not be null!")
    private VirusMutation mutation;

    @NumberRange(min = 0, max = 100, message = "Invalid turnover rate! Should be in range 0-100!")
    private int turnoverRate;

    @NumberRange(min = 1, max = 12, message = "Invalid hours until! Should be in range 0-12!")
    private int hoursUntilTurn;

    @NotNull(message = "VirusMagnitude can not be null!")
    private VirusMagnitude magnitude;

//    @NotNull(message = "Release Date can not be null!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @BeforeToday
    private LocalDate releasedOn;

    public VirusCreateBindingModel() {
        this.setHoursUntilTurn(1);
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
}
