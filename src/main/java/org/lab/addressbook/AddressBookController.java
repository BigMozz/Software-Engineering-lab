package org.lab.addressbook;

import org.lab.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AddressBookController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @GetMapping("/addressbooks")
    public Iterable<AddressBook> getAllAddressBooks(){
        return addressBookRepository.findAll();
    }

    @PostMapping("/addressbooks")
    public AddressBook createAddressBook(){
        AddressBook newAddressBook = new AddressBook();
        return addressBookRepository.save(newAddressBook);
    }

    @GetMapping("/addressbooks/{id}")
    public AddressBook getAddressBookById(@PathVariable long id){
        return addressBookRepository.findById(id).orElseThrow(() -> new RuntimeException("Address Book Not Found with id: " + id));
    }

    @PostMapping("/addressbooks/{id}/buddies")
    public AddressBook addBuddyToAddressBook(@PathVariable long id, @RequestBody BuddyInfo buddy) {
        AddressBook addressBook = addressBookRepository.findById(id).orElseThrow(() -> new RuntimeException("AddressBook not found with id: " + id));

        BuddyInfo newBuddy = new BuddyInfo(buddy.getBuddyName(), buddy.getBuddyNumber());
        addressBook.addBuddy(newBuddy);

        return addressBookRepository.save(addressBook);
    }

    @DeleteMapping("/addressbooks/{addressBookId}/buddies/{buddyId}")
    public AddressBook removeBuddyFromAddressBook(
            @PathVariable long addressBookId,
            @PathVariable long buddyId) {

        AddressBook addressBook = addressBookRepository.findById(addressBookId)
                .orElseThrow(() -> new RuntimeException("AddressBook not found with id: " + addressBookId));

        // Find and remove the buddy
        BuddyInfo buddyToRemove = addressBook.getBuddies().stream()
                .filter(buddy -> buddy.getId().equals(buddyId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Buddy not found with id: " + buddyId));

        addressBook.getBuddies().remove(buddyToRemove);

        return addressBookRepository.save(addressBook);
    }
}
