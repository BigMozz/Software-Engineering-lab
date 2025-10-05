package org.lab.addressbook.repository;

import java.util.List;

import org.lab.addressbook.AddressBook;
import org.lab.addressbook.BuddyInfo;
import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {
    List<AddressBook> findByBuddiesContaining(BuddyInfo buddy);
}
