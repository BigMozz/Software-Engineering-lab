package org.lab.addressbook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressBookTest {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    public void addBuddy() {
        AddressBook addressBook = new AddressBook();
        assertEquals("New AddressBook should have 0 buddies", 0, addressBook.getBuddies().size());

        BuddyInfo newBuddy = new BuddyInfo("Test Buddy", "613-000-0000");
        addressBook.addBuddy(newBuddy);

        assertEquals("AddressBook should have 1 buddy after adding", 1, addressBook.getBuddies().size());
    }

    @Test
    public void getBuddies() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy1 = new BuddyInfo("First", "613-111-1111");
        BuddyInfo buddy2 = new BuddyInfo("Second", "613-222-2222");

        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        List<BuddyInfo> retrievedBuddies = addressBook.getBuddies();

        assertEquals("Should have retrieved 2 buddies", 2, retrievedBuddies.size());
    }

    @Test
    public void testPersistAddressBook(){
        BuddyInfo buddy1 = new BuddyInfo("Jared", "613-998-2143");
        BuddyInfo buddy2 = new BuddyInfo("Phillip", "613-975-9265");

        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        AddressBook savedAddressBook = addressBookRepository.save(addressBook);

        assertNotNull("Saved AddressBook should have an ID", savedAddressBook.getId());

        Optional<AddressBook> foundAddressBook = addressBookRepository.findById(savedAddressBook.getId());
        assertTrue("Should be able to find the saved AddressBook by ID", foundAddressBook.isPresent());

        AddressBook retrievedBook = foundAddressBook.get();
        assertEquals("AddressBook should contain 2 buddies", 2, retrievedBook.getBuddies().size());

        System.out.println("Saved AddressBook contains: " + retrievedBook.getBuddies().size() + " buddies");
        System.out.println("AddressBook ID: " + retrievedBook.getId());
        for (BuddyInfo buddy : retrievedBook.getBuddies()) {
            System.out.println("   - Buddy: " + buddy.getBuddyName() + " (ID: " + buddy.getId() + ")");
        }
    }
}