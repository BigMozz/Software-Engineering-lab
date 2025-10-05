package org.lab.addressbook;

import org.lab.addressbook.repository.AddressBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataJpaApplication {
    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository repository) {
        return args -> {
            AddressBook addressBook = new AddressBook();
            addressBook.addBuddy(new BuddyInfo("Jared", "613-687-9284"));
            addressBook.addBuddy(new BuddyInfo("Phillip", "613-998-2143"));

            repository.save(addressBook);

            log.info("AddressBooks found with findAll():");
            log.info("-------------------------------");
            for (AddressBook ab : repository.findAll()) {
                log.info(ab.toString());
                for (BuddyInfo buddy : ab.getBuddies()) {
                    log.info("  - " + buddy.toString());
                }
            }

            AddressBook savedAddressBook = repository.findAll().iterator().next();
            if (!savedAddressBook.getBuddies().isEmpty()) {
                BuddyInfo persistedBuddy = savedAddressBook.getBuddies().get(0);
                log.info("AddressBooks found with findByBuddiesContaining for buddy: " + persistedBuddy.toString());
                log.info("--------------------------------------------");
                for (AddressBook ab : repository.findByBuddiesContaining(persistedBuddy)) {
                    log.info(ab.toString());
                }
            }
            log.info("");
        };
    }
}