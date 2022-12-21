package by.clevertec.checks.demoArtifact.dao.api;

import by.clevertec.checks.demoArtifact.dao.entity.SalesReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ISalesReceiptStorage extends JpaRepository<SalesReceiptEntity, UUID> {
    List<SalesReceiptEntity> findAllByIdFinalReceipt (UUID idFinalReceipt);

    void deleteAllByIdFinalReceipt (UUID idFinalReceipt);

}
