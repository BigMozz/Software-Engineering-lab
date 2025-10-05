package org.lab.addressbook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BuddyInfoTest {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    public void testCreateAndSaveBuddyInfo() {
        // Create an AddressBook and add a BuddyInfo to it
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Jared", "613-998-2143");
        addressBook.addBuddy(buddy);

        // Save through the repository (BuddyInfo will be cascaded)
        AddressBook savedAddressBook = addressBookRepository.save(addressBook);

        // Verify the BuddyInfo was saved with the AddressBook
        assertNotNull("Saved AddressBook should have an ID", savedAddressBook.getId());
        assertEquals("AddressBook should have 1 buddy", 1, savedAddressBook.getBuddies().size());

        BuddyInfo savedBuddy = savedAddressBook.getBuddies().get(0);
        assertNotNull("Saved BuddyInfo should have an ID", savedBuddy.getId());
        assertEquals("Buddy name should match", "Jared", savedBuddy.getBuddyName());
        assertEquals("Buddy number should match", "613-998-2143", savedBuddy.getBuddyNumber());
    }

    @Test
    public void testBuddyInfoGettersAndSetters() {
        BuddyInfo buddy = new BuddyInfo();
        buddy.setBuddyName("Test Name");
        buddy.setBuddyNumber("123-456-7890");

        assertEquals("Getter for name should work", "Test Name", buddy.getBuddyName());
        assertEquals("Getter for number should work", "123-456-7890", buddy.getBuddyNumber());
    }

    @Test
    public void testBuddyInfoConstructor() {
        BuddyInfo buddy = new BuddyInfo("Constructor Test", "987-654-3210");

        assertEquals("Constructor should set name", "Constructor Test", buddy.getBuddyName());
        assertEquals("Constructor should set number", "987-654-3210", buddy.getBuddyNumber());
    }

    @Test
    public void testBuddyInfoToString() {
        BuddyInfo buddy = new BuddyInfo("ToString Test", "555-123-4567");
        String expectedString = "ToString Test (555-123-4567)";

        assertEquals("toString should return expected format", expectedString, buddy.toString());
    }
}