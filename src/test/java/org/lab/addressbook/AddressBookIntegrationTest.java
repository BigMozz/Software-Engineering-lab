package org.lab.addressbook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAddressBook() {
        AddressBook response = this.restTemplate.postForObject("http://localhost:" + port + "/api/addressbooks", null, AddressBook.class);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testGetAllAddressBooks(){
        AddressBook[] response = this.restTemplate.getForObject("http://localhost:" + port + "/api/addressbooks", AddressBook[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void testGetAddressBookById(){
        AddressBook response = this.restTemplate.getForObject("http://localhost:" + port + "/api/addressbooks/1", AddressBook.class);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testAddBuddyToAddressBook() {
        AddressBook addressBook = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks",
                null,
                AddressBook.class
        );

        BuddyInfo buddy = new BuddyInfo();
        buddy.setBuddyName("Test Buddy");
        buddy.setBuddyNumber("555-1234");

        AddressBook response = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks/" + addressBook.getId() + "/buddies",
                buddy,
                AddressBook.class
        );

        assertThat(response).isNotNull();
        assertThat(response.getBuddies()).hasSize(1);
        assertThat(response.getBuddies().get(0).getBuddyName()).isEqualTo("Test Buddy");
    }

    @Test
    public void testRemoveBuddyFromAddressBook() {
        AddressBook addressBook = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks",
                null,
                AddressBook.class
        );

        BuddyInfo buddy = new BuddyInfo();
        buddy.setBuddyName("Test Buddy");
        buddy.setBuddyNumber("555-1234");

        AddressBook addressBookWithBuddy = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks/" + addressBook.getId() + "/buddies",
                buddy,
                AddressBook.class
        );

        Long buddyId = addressBookWithBuddy.getBuddies().get(0).getId();

        AddressBook response = this.restTemplate.exchange(
                "http://localhost:" + port + "/api/addressbooks/" + addressBook.getId() + "/buddies/" + buddyId,
                org.springframework.http.HttpMethod.DELETE,
                null,
                AddressBook.class
        ).getBody();

        assertThat(response).isNotNull();
        assertThat(response.getBuddies()).isEmpty();
    }

}
