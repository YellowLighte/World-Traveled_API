package com.worldtravel.demo.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    // Adventures are tied to Country
    @OneToMany(mappedBy = "country", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Adventure> adventureList;



    public Country() {
    }

    public Country(Long id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


//    public List<Adventure> getAdventureList() {
//        return adventureList;
//    }

    public void setAdventureList(List<Adventure> adventureList) {
        this.adventureList = adventureList;
    }
}
