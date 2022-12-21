package by.clevertec.checks.demoArtifact.model.services.api;


import by.clevertec.checks.demoArtifact.model.dto.DiscountCard;
import by.clevertec.checks.demoArtifact.model.dto.ListDiscountCard;

import java.util.List;
import java.util.UUID;

public interface IDiscountCardService {
    /*
    Сервис для работы с дисконтными картами
     */
    DiscountCard getByUuid(UUID id);

    DiscountCard getByBarcode(Integer barcode);

    List<DiscountCard> getAll();


    DiscountCard save(DiscountCard discountCard);
    List<DiscountCard> saveAll (ListDiscountCard discountCards);

    DiscountCard updateByUuid(UUID id, DiscountCard discountCard);

    DiscountCard updateByBarcode(Integer barcode, DiscountCard discountCard);

    List<DiscountCard> updateAndSave (ListDiscountCard discountCards);

    DiscountCard deleteByUuid(UUID id);

    DiscountCard deleteByBarcode(Integer barcode);

    List<DiscountCard> deleteAll();

    Integer getDiscount (UUID id);

}
