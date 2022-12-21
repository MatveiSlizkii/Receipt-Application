package by.clevertec.checks.demoArtifact.model.services.api;

import by.clevertec.checks.demoArtifact.model.dto.ProductInfo;
import by.clevertec.checks.demoArtifact.model.dto.SalesReceipt;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ISalesReceiptService {

        /*
        Сервис для работы чеками в части списка товаров и скидки за счет количества
        update в чеках не делал, так как на практике данные чека изменять нельзя, только кдалить и пересоздать заново
         */
        SalesReceipt save (UUID idFinalReceipt, UUID idProduct, Integer quantity);

        List<SalesReceipt> saveAll (UUID idFinalReceipt, ProductInfo productInfo);

        SalesReceipt get(UUID id);

        List<SalesReceipt> getAllByIdFinalReceipt (UUID idFinalReceipt);

        List<Double> getGrandTotal (List<SalesReceipt> salesReceipts);

        void deleteAllByIdFinalReceipt (UUID idFinalReceipt);

}
