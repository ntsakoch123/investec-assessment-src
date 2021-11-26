package za.co.investec.assessment.clientmanagement.domain.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import za.co.investec.assessment.clientmanagement.domain.model.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ClientManagementRepositoryTest {

    @Autowired
    ClientManagementRepository clientManagementRepository;

    @Test
    public void test_create_thenUpdate_thenFind() {
        Client client = sampleClient();

        clientManagementRepository.save(client);

        Optional<Client>  foundById  = clientManagementRepository.findById(1L);
       assertTrue(foundById.isPresent(), "client by id not found");
        assertFalse(foundById.get().getPhysicalAddress().isPresent(),"physical address must be empty when not updated");

        Optional<Client>  foundByMobileNumber = clientManagementRepository.findByMobileNumber(new MobileNumber("0839657359"));
        assertTrue(foundByMobileNumber.isPresent(), "client by mobile number not found");

        List<Client> foundByAnyField = clientManagementRepository.findByIdNumberOrFirstNameOrMobileNumber(null, new FirstName("Ntsako"),null);
        assertFalse(foundByAnyField.isEmpty(), "client by any field not found");

        Client updated = sampleClient();
        PhysicalAddress physicalAddress = new PhysicalAddress(12,"ST","SB",98);
        updated.updatePhysicalAddress(physicalAddress);
        clientManagementRepository.save(updated);

        Optional<Client>  findUpdated  = clientManagementRepository.findById(1L);
        assertTrue(findUpdated.isPresent(),"updated client not found");
        assertTrue(updated.getPhysicalAddress().isPresent(),"physical address must not be empty after updated");

    }

    private Client sampleClient() {
        FirstName firstName = new FirstName("Ntsako");
        LastName lastName = new LastName("Chabalala");
        IdNumber idNumber = new IdNumber("8605065397083");
        MobileNumber mobileNumber = new MobileNumber("0839657359");
        return new Client(firstName, lastName, idNumber, mobileNumber);
    }
}