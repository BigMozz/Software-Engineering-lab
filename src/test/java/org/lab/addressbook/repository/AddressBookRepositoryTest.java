package org.lab.addressbook.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.addressbook.AddressBook;
import org.lab.addressbook.BuddyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressBookRepositoryTest {

    @Autowired
    private AddressBookRepository repository;

    @Test
    public void testSaveAndFindAll() {
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(new BuddyInfo("Test", "123-456-7890"));
        repository.save(addressBook);

        List<AddressBook> addressBooks = (List<AddressBook>) repository.findAll();
        assertEquals(1, addressBooks.size());
        assertEquals(1, addressBooks.get(0).getBuddies().size());
    }

    @Test
    public void testFindByBuddiesContaining() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Test", "123-456-7890");
        addressBook.addBuddy(buddy);
        repository.save(addressBook);

        // Retrieve the saved buddy from the database to ensure it has an ID
        AddressBook savedAddressBook = repository.findAll().iterator().next();
        BuddyInfo savedBuddy = savedAddressBook.getBuddies().get(0);

        List<AddressBook> result = repository.findByBuddiesContaining(savedBuddy);
        assertEquals(1, result.size());
        assertEquals(savedAddressBook.getId(), result.get(0).getId());
    }
}