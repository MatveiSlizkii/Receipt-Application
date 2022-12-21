package by.clevertec.checks.demoArtifact.dao.api;

import by.clevertec.checks.demoArtifact.dao.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductStorage extends JpaRepository<ProductEntity, UUID> {
   boolean existsByBarcode(int barcode);
   ProductEntity findByBarcode (int barcode);
   boolean deleteByBarcode (int barcode);

}
