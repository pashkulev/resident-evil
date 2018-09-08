package com.vankata.residentevil.service;

import com.vankata.residentevil.domain.model.service.CapitalServiceModel;
import com.vankata.residentevil.domain.model.view.CapitalViewModelPlusSelected;
import com.vankata.residentevil.domain.model.binding.VirusEditBindingModel;
import com.vankata.residentevil.domain.entity.Capital;
import com.vankata.residentevil.repository.CapitalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CapitalServiceImpl implements CapitalService {

    private final CapitalRepository capitalRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CapitalServiceModel> findAllCapitals() {
        List<Capital> capitals = this.capitalRepository.findAllByOrderByName();
        CapitalServiceModel[] capitalDtos = this.modelMapper.map(capitals, CapitalServiceModel[].class);
        return Arrays.asList(capitalDtos);
    }

    @Override
    public List<CapitalViewModelPlusSelected> findAllCapitalsPlusSelected(VirusEditBindingModel virus) {
        List<Capital> capitals = this.capitalRepository.findAllByOrderByName();
        CapitalViewModelPlusSelected[] capitalDtos = this.modelMapper.map(capitals, CapitalViewModelPlusSelected[].class);
        List<CapitalServiceModel> affectedCapitalDtos = virus.getCapitals();

        for (CapitalViewModelPlusSelected capitalDto : capitalDtos) {
            for (CapitalServiceModel affectedCapitalDto : affectedCapitalDtos) {
                if (capitalDto.getId() == affectedCapitalDto.getId()) {
                    capitalDto.setIsSelected(true);
                    break;
                }
            }
        }

        return Arrays.asList(capitalDtos);
    }
}
