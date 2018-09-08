package com.vankata.residentevil.domain.model.view;

import com.vankata.residentevil.domain.model.service.CapitalServiceModel;

public class CapitalViewModelPlusSelected extends CapitalServiceModel {

    private boolean isSelected;

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean selected) {
        this.isSelected = selected;
    }
}
