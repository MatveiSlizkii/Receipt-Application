package by.clevertec.checks.demoArtifact.dao.api;

import by.clevertec.checks.demoArtifact.dao.entity.DiscountCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDiscountCardStorage extends JpaRepository<DiscountCardEntity, UUID> {
    DiscountCardEntity findByBarcode (Integer barcode);
    boolean existsByBarcode(Integer barcode);

}
