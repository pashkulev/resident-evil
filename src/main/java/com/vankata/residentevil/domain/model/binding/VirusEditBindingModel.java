package com.vankata.residentevil.domain.model.binding;

import com.vankata.residentevil.domain.model.service.CapitalServiceModel;

import java.util.ArrayList;
import java.util.List;

public class VirusEditBindingModel extends VirusCreateBindingModel {

    private List<CapitalServiceModel> capitals;

    public VirusEditBindingModel() {
        this.capitals = new ArrayList<>();
    }

    public List<CapitalServiceModel> getCapitals() {
        return this.capitals;
    }

    public void setCapitals(List<CapitalServiceModel> capitals) {
        this.capitals = capitals;
    }
}
