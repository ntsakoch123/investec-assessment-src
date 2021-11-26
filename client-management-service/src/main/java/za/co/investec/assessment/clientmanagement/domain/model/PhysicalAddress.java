package za.co.investec.assessment.clientmanagement.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import za.co.investec.assessment.clientmanagement.domain.shared.ValueObject;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@ToString
@Table(name = "clients_addresses")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhysicalAddress implements ValueObject<PhysicalAddress> {

    @Id
    @Column(name = "physical_address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long physicalAddressId;

    @Column
    private long houseNumber;

    @Column
    private String streetName;

    @Column
    private String suburb;

    @Column
    private long postalCode;

    public PhysicalAddress(long houseNumber, String streetName, String suburb, long postalCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postalCode = postalCode;
    }


    @Override
    public int hashCode() {
        return Objects.hash(streetName, houseNumber, suburb, postalCode);
    }

    @Override
    public boolean sameValueAs(PhysicalAddress other) {
        return Objects.equals(streetName, other.streetName) &&
                Objects.equals(houseNumber, other.houseNumber) &&
                Objects.equals(suburb, other.suburb) &&
                Objects.equals(postalCode, other.postalCode);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof PhysicalAddress)) return false;
        return sameValueAs((PhysicalAddress) other);
    }
}
