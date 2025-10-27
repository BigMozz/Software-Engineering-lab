package org.lab.addressbook;

import org.lab.addressbook.AddressBook;
import org.lab.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @GetMapping("/addressbooks/{id}/view")
    public String viewAddressBook(@PathVariable long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id).orElseThrow(() -> new RuntimeException("AddressBook not found with id: " + id));

        model.addAttribute("addressBook", addressBook);
        return "addressbook-view";
    }

    @PostMapping("/addressbooks/create")
    public String createAddressBook() {
        AddressBook newAddressBook = new AddressBook();
        AddressBook savedAddressBook = addressBookRepository.save(newAddressBook);
        return "redirect:/addressbooks/" + savedAddressBook.getId() + "/view";
    }

    @PostMapping("/addressbooks/{id}/buddies/add")
    public String addBuddyToAddressBook(@PathVariable long id, @RequestParam String name,
                                        @RequestParam String phone) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AddressBook not found with id: " + id));

        BuddyInfo newBuddy = new BuddyInfo(name, phone);
        addressBook.addBuddy(newBuddy);
        addressBookRepository.save(addressBook);

        return "redirect:/addressbooks/" + id + "/view";
    }

}