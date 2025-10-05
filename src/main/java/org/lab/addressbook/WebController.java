package org.lab.addressbook;

import org.lab.addressbook.AddressBook;
import org.lab.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}