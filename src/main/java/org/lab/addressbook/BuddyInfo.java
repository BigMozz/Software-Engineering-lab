package org.lab.addressbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class BuddyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buddyName;
    private String buddyNumber;

    @ManyToOne
    @JsonIgnore
    private AddressBook addressBook;

    public BuddyInfo(){
    }

    public BuddyInfo(String buddyName, String buddyNumber){
        this.buddyName = buddyName;
        this.buddyNumber = buddyNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuddyName(){
        return buddyName;
    }

    public String getBuddyNumber(){
        return buddyNumber;
    }

    public void setBuddyName(String buddyName){
        this.buddyName = buddyName;
    }

    public void setBuddyNumber(String buddyNumber){
        this.buddyNumber = buddyNumber;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public String toString() {
        return buddyName + " (" + buddyNumber + ")";
    }
}
