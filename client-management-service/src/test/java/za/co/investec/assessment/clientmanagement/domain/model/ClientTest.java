package za.co.investec.assessment.clientmanagement.domain.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientTest {

    private static Client client;

    @BeforeAll
    public static void init(){
        FirstName firstName = new FirstName("Ntsako");
        LastName lastName = new LastName("Chabalala");
        IdNumber idNumber = new IdNumber("8605065397083");
        MobileNumber mobileNumber = new MobileNumber("0839657359");

        client = new Client(firstName, lastName, idNumber, mobileNumber);

    }

    @Test
    @Order(1)
    public void test_when_physicalAddress_isNull_thenUpdate(){
        PhysicalAddress physicalAddress = new PhysicalAddress(123,"PPP","JJJ",15);
        assertTrue(client.updatePhysicalAddress(physicalAddress));
    }

    @Test
    @Order(2)
    public void test_when_update_physicalAddress_isCalled_thenDoNotUpdate_when_notChanged(){
        PhysicalAddress physicalAddress = new PhysicalAddress(123,"PPP","JJJ",15);
        assertFalse(client.updatePhysicalAddress(physicalAddress));
    }

    @Test
    @Order(3)
    public void test_when_physicalAddress_isPresent_thenUpdate_when_changed(){
        PhysicalAddress physicalAddress = new PhysicalAddress(123,"PPP","JJJ Updated",15);
        assertTrue(client.updatePhysicalAddress(physicalAddress));
    }

    @Test
    @Order(4)
    public void test_when_physicalAddress_is_present_and_removed_thenUpdate(){
        assertTrue(client.removePhysicalAddress());
        assertTrue(client.getPhysicalAddress().isEmpty());
    }

    @Test
    @Order(5)
    public void test_when_physicalAddress_is_not_present_and_removed_thenDoNothing(){
        assertFalse(client.removePhysicalAddress());
    }

    @Test
    @Order(6)
    public void test_when_mobileNumber_isNull_thenUpdate(){
        assertTrue(client.updateMobileNumber(new MobileNumber("0123654789")));
    }

    @Test
    @Order(7)
    public void test_when_update_mobileNumber_isCalled_thenDoNotUpdate_when_notChanged(){
        assertFalse(client.updateMobileNumber(new MobileNumber("0123654789")));
    }

    @Test
    @Order(8)
    public void test_when_details_changed_thenUpdate(){
        FirstName firstName = new FirstName("Ntsako");
        LastName lastName = new LastName("Chabalala Updated");
        IdNumber idNumber = new IdNumber("8605065397083");
        assertTrue(client.updateDetails(firstName, lastName,idNumber));
    }

    @Test
    @Order(9)
    public void test_when_details_not_changed_then_doNothing(){
        FirstName firstName = new FirstName("Ntsako");
        LastName lastName = new LastName("Chabalala Updated");
        IdNumber idNumber = new IdNumber("8605065397083");
        assertFalse(client.updateDetails(firstName, lastName,idNumber));
    }

    @Test
    public void testObjectsEqualsByClientId(){
        Client other = new Client();
        assertEquals(client, other);
    }
}