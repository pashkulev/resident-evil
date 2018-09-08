package com.vankata.residentevil.service;

import com.vankata.residentevil.domain.model.service.CapitalServiceModel;
import com.vankata.residentevil.domain.model.view.CapitalViewModelPlusSelected;
import com.vankata.residentevil.domain.model.binding.VirusEditBindingModel;

import java.util.List;

public interface CapitalService {

    List<CapitalServiceModel> findAllCapitals();

    List<CapitalViewModelPlusSelected> findAllCapitalsPlusSelected(VirusEditBindingModel virus);
}
