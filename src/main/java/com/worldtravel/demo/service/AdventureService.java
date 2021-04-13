package com.worldtravel.demo.service;

import com.worldtravel.demo.exception.InformationExistsException;
import com.worldtravel.demo.exception.InformationNotFoundException;
import com.worldtravel.demo.model.Adventure;
import com.worldtravel.demo.model.Country;
import com.worldtravel.demo.repository.AdventureRepository;
import com.worldtravel.demo.repository.CountryRepository;
import com.worldtravel.demo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdventureService {
    private CountryRepository countryRepository;
    private AdventureRepository adventureRepository;

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    @Autowired
    public void setAdventureRepository(AdventureRepository adventureRepository){
        this.adventureRepository = adventureRepository;
    }

    public List<Adventure> getAdventures (){
        System.out.println("service calling getCountries =====>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Adventure> adventureList = adventureRepository.findByUserId(myUserDetails.getUser().getId());

        if(adventureList.isEmpty()){
            throw new InformationNotFoundException("no adventures found for that user with id " + myUserDetails.getUser().getId());
        }
        else{
            return adventureList;
        }
    }

    public Adventure getAdventure(Long adventureId){
        System.out.println("calling getAdventure =====>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Adventure adventure = adventureRepository.findByIdAndUserId(adventureId, myUserDetails.getUser().getId());
        if(adventure == null){
            throw new InformationNotFoundException("Adventure with id " + adventureId + " not found");
        }
        else{
            return adventure;
        }

    }

    public Adventure createAdventure(Adventure adventureObject) {
        System.out.println("calling createAdventure =====>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Country> country = Optional.ofNullable(countryRepository.findByName(adventureObject.getCountryName()));

        if (!country.isPresent()) {
            throw new InformationNotFoundException("Country with provided name " + adventureObject.getCountryName() + " is not found");
        }
        else{
            adventureObject.setUser(myUserDetails.getUser());
            adventureObject.setCountry(country.get());
            return adventureRepository.save(adventureObject);
        }
    }

    public Set<Country> getCountries(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Country> countries = getAdventures().stream().map(adventure -> {
            String countryName = adventure.getCountryName();
            return countryRepository.findByName(countryName);
        }).collect(Collectors.toSet());

        if(countries.isEmpty()){
            throw new InformationNotFoundException("Countries not found");
        }
        else{
            return countries;
        }
    }

    public Country getCountry(Long adventureId, Long countryId){
        System.out.println("calling getCountry =====>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Adventure adventure = adventureRepository.findByIdAndUserId(adventureId, myUserDetails.getUser().getId());

        if(adventure != null){
           // Optional<Country> country = countryRepository.findById(countryId); //.stream().filter(c -> c.getId().equals(countryId)).findFirst();
            Optional<Country> country = Optional.ofNullable(adventure.getCountry());
            if(country.isPresent()){
                return country.get();
            }
            else{
                throw new InformationNotFoundException("Adventure with id " + adventureId + " not found");
            }
        }
        else{
            throw new InformationNotFoundException("Country with id " + countryId + " not found");
        }
    }
}

