package com.worldtravel.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "adventures")
public class Adventure {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column
    private String adventureName;

    @Column
    private String dateWent;

    @Column
    private String adventureDescription;

    @Column
    private String countryName;

    // Connection to the User model, allowing User:Country to act as Many:Many
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Connection to the Control model, allowing User:Country to act as Many:Many
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Adventure() {
    }

    public Adventure(Long id, String adventureName, String dateWent, String adventureDescription, String countryName) {
        this.id = id;
        this.adventureName = adventureName;
        this.dateWent = dateWent;
        this.adventureDescription = adventureDescription;
        this.countryName = countryName.toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateWent() {
        return dateWent;
    }

    public void setDateWent(String dateWent) {
        this.dateWent = dateWent;
    }

    public String getAdventureDescription() {
        return adventureDescription;
    }

    public void setAdventureDescription(String adventureDescription) {
        this.adventureDescription = adventureDescription;
    }

    public String getAdventureName() {
        return adventureName;
    }

    public void setAdventureName(String adventureName) {
        this.adventureName = adventureName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Adventure{" +
                "id=" + id +
                ", adventureName='" + adventureName + '\'' +
                ", dateWent='" + dateWent + '\'' +
                ", adventureDescription='" + adventureDescription + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
