package com.vankata.residentevil.service;

import com.vankata.residentevil.domain.model.binding.VirusCreateBindingModel;
import com.vankata.residentevil.domain.model.binding.VirusEditBindingModel;

import java.util.List;

public interface VirusService {

    VirusEditBindingModel findById(long id);

    List<VirusCreateBindingModel> findAll();

    boolean addVirus(VirusCreateBindingModel virusDto, int[] capitalIds);

    void editVirus(VirusCreateBindingModel virusDto, int[] capitalIds);

    String deleteVirus(long id);

    String findAllMapViruses();
}
