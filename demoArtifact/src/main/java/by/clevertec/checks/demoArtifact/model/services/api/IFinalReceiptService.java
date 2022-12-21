package by.clevertec.checks.demoArtifact.model.services.api;

import by.clevertec.checks.demoArtifact.model.dto.FinalReceipt;
import by.clevertec.checks.demoArtifact.model.dto.ProductInfo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IFinalReceiptService {
        /*
        Сервис для работы чеками в части полной информации по чеку
        update в чеках не делал, так как на практике данные чека изменять нельзя, только кдалить и пересоздать заново
         */
        FinalReceipt saveAll (UUID idDiscountCard, ProductInfo productInfo);

        FinalReceipt get(UUID id);

        void delete (UUID id);

}
