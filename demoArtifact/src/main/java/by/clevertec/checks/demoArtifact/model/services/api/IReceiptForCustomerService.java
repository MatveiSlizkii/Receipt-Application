package by.clevertec.checks.demoArtifact.model.services.api;


import by.clevertec.checks.demoArtifact.model.dto.DiscountCard;
import by.clevertec.checks.demoArtifact.model.dto.ReceiptForCustomer;

import java.util.List;
import java.util.UUID;

public interface IReceiptForCustomerService {
        /*
        Сервис для работы чеками в читабельном формате для покупателей и последующего вывода
        update в чеках не делал, так как на практике данные чека изменять нельзя, только кдалить и пересоздать заново
         */
        ReceiptForCustomer formAndReceive (UUID idFinalReceipt);

        byte[] getReceiptExcel (UUID idFinalReceipt);

}
