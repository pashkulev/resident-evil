package com.vankata.residentevil.service;

import com.vankata.residentevil.domain.model.binding.VirusCreateBindingModel;
import com.vankata.residentevil.domain.model.binding.VirusEditBindingModel;
import com.vankata.residentevil.domain.entity.Capital;
import com.vankata.residentevil.domain.entity.Virus;
import com.vankata.residentevil.repository.CapitalRepository;
import com.vankata.residentevil.repository.VirusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class VirusServiceImpl implements VirusService {

    private final VirusRepository virusRepository;

    private final CapitalRepository capitalRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository,
                            CapitalRepository capitalRepository,
                            ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VirusEditBindingModel findById(long id) {
        Virus virus = this.virusRepository.findById(id).orElse(null);
        if (virus != null) {
            return this.modelMapper.map(virus, VirusEditBindingModel.class);
        }

        return null;
    }

    @Override
    public List<VirusCreateBindingModel> findAll() {
        List<Virus> viruses = this.virusRepository.findAll();
        VirusCreateBindingModel[] virusDtos = this.modelMapper.map(viruses, VirusCreateBindingModel[].class);
        return Arrays.asList(virusDtos);
    }

    @Override
    public boolean addVirus(VirusCreateBindingModel virusDto, int[] capitalIds) {
        Virus virus = this.getVirusEntity(virusDto, capitalIds);
        return this.virusRepository.save(virus).getId() != 0;
    }

    @Override
    public void editVirus(VirusCreateBindingModel virusDto, int[] capitalIds) {
        Virus virus = this.getVirusEntity(virusDto, capitalIds);
        this.virusRepository.save(virus);
    }

    @Override
    @Transactional
    public String deleteVirus(long id) {
        Virus virus = this.virusRepository.findById(id).orElse(null);

        if (virus != null) {
            this.virusRepository.deleteById(id);
            return virus.getName();
        }

        return null;
    }

    private Virus getVirusEntity(VirusCreateBindingModel virusDto, int[] capitalIds) {
        Virus virus = this.modelMapper.map(virusDto, Virus.class);
        if (capitalIds != null) {
            for (long capitalId : capitalIds) {
                Capital capital = this.capitalRepository.findById(capitalId).orElse(null);
                virus.getCapitals().add(capital);
            }
        }

        return virus;
    }

    @Override
    public String findAllMapViruses() {
        StringBuilder geoJson = new StringBuilder();
        geoJson.append("{\n")
                .append("\t\"type\": \"FeatureCollection\",\n")
                .append("\t\"features\": [\n");

        List<Virus> viruses = this.virusRepository.findAll();
        for (Virus virus : viruses) {
            int magnitude = 0;
            switch (virus.getMagnitude()) {
                case LOW: magnitude = 4; break;
                case MEDIUM: magnitude = 5; break;
                case HIGH: magnitude = 6; break;
            }

            for (Capital capital : virus.getCapitals()) {
                geoJson.append("\t{")
                        .append("\t\t\"type\": \"Feature\",\n")
                        .append("\t\t\"properties\": {\n")
                        .append(String.format("\t\t\t\"mag\": %d,\n", magnitude))
                        .append("\t\t\t\"color\": \"#f00\"\n")
                        .append("\t\t},")
                        .append("\t\t\"geometry\": {\n")
                        .append("\t\t\t\"type\": \"Point\",\n")
                        .append("\t\t\t\"coordinates\": [\n")
                        .append(String.format("\t\t\t\t%.6f,\n", capital.getLatitude()))
                        .append(String.format("\t\t\t\t%.6f\n", capital.getLongitude()))
                        .append("\t\t\t]\n")
                        .append("\t\t}\n")
                        .append("\t},\n");
            }
        }

        geoJson = geoJson.delete(geoJson.lastIndexOf(","), geoJson.length());
        geoJson.append("]}");

        return geoJson.toString();
    }
}
