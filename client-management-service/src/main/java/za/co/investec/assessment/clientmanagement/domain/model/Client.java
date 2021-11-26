package za.co.investec.assessment.clientmanagement.domain.model;

import lombok.*;
import za.co.investec.assessment.clientmanagement.domain.shared.EntityObject;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Entity
@ToString
@Table(name = "clients")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client implements EntityObject<Client> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clientId;

    @Embedded
    private FirstName firstName;

    @Embedded
    private LastName lastName;

    @Embedded
    private IdNumber idNumber;

    @Embedded
    @Getter(AccessLevel.NONE)
    private MobileNumber mobileNumber;

    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "physical_address_id")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private PhysicalAddress physicalAddress;

    public Client(FirstName firstName, LastName lastName, IdNumber idNumber, MobileNumber mobileNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.mobileNumber = mobileNumber;
    }

    public Optional<PhysicalAddress> getPhysicalAddress() {
        return physicalAddress != null ? Optional.of(physicalAddress) : Optional.empty();
    }

    public Optional<MobileNumber> getMobileNumber() {
        return mobileNumber != null ? Optional.of(mobileNumber) : Optional.empty();
    }

    public boolean updatePhysicalAddress(PhysicalAddress newAddress) {
        if (this.physicalAddress == null || !this.physicalAddress.sameValueAs(newAddress)) {
            this.physicalAddress = newAddress;
            return true;
        }
        return false;
    }

    public boolean updateDetails(FirstName firstName, LastName lastName, IdNumber idNumber) {
        boolean updated = false;
        if (!this.firstName.sameValueAs(firstName.getValue())) {
            this.firstName = firstName;
            updated = true;
        }
        if (!this.lastName.sameValueAs(lastName.getValue())) {
            this.lastName = lastName;
            updated = true;
        }
        if (!this.idNumber.sameValueAs(idNumber.getValue())) {
            this.idNumber = idNumber;
            updated = true;
        }
        return updated;
    }

    public boolean updateMobileNumber(MobileNumber mobileNumber) {
        if (this.mobileNumber == null) {
            this.mobileNumber = mobileNumber;
            return true;
        } else if (!this.mobileNumber.sameValueAs(mobileNumber.getValue())) {
            this.mobileNumber = mobileNumber;
            return true;
        }
        return false;
    }

    public boolean removeMobileNumber() {
        if (this.mobileNumber != null) {
            this.mobileNumber = null;
            return true;
        }
        return  false;
    }

    public boolean removePhysicalAddress() {
        if (physicalAddress != null) {
            this.physicalAddress = null;
            return true;
        }
        return  false;
    }

    @Override
    public boolean sameIdentityAs(Client other) {
        if (this == other) return true;
        return this.clientId == other.clientId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Client)) return false;
        return sameIdentityAs((Client) other);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
