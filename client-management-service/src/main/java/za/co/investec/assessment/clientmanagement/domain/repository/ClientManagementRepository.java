package za.co.investec.assessment.clientmanagement.domain.repository;

import za.co.investec.assessment.clientmanagement.domain.model.*;
import za.co.investec.assessment.clientmanagement.infrastructure.JPARepository;

import java.util.List;
import java.util.Optional;

public interface ClientManagementRepository extends JPARepository<Client, Long> {

    Optional<Client> findByIdNumber(IdNumber idNumber);

    Optional<Client> findByMobileNumber(MobileNumber mobileNumber);

    List<Client> findByIdNumberOrFirstNameOrMobileNumber(IdNumber idNumber, FirstName firstName, MobileNumber mobileNumber);

}
