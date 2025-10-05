package org.lab.addressbook;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BuddyInfo> buddies = new ArrayList<>();

    public AddressBook(){
    }

    public void addBuddy(BuddyInfo buddy){
        buddies.add(buddy);
        buddy.setAddressBook(this);
    }

    public List<BuddyInfo> getBuddies(){
        return buddies;
    }

    public void setBuddies(List<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "id=" + id +
                ", buddies=" + buddies +
                '}';
    }

    public static void main(String[] args){
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(new BuddyInfo("Jared", "613-687-9284"));
        addressBook.addBuddy(new BuddyInfo("Phillip", "613-998-2143"));

        for (BuddyInfo buddy : addressBook.getBuddies()){
            System.out.println(buddy);
        }
    }
}