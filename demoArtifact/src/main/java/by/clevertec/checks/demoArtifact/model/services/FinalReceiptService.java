package by.clevertec.checks.demoArtifact.model.services;

import by.clevertec.checks.demoArtifact.dao.api.IFinalReceiptStorage;
import by.clevertec.checks.demoArtifact.dao.entity.FinalReceiptEntity;
import by.clevertec.checks.demoArtifact.model.dto.FinalReceipt;
import by.clevertec.checks.demoArtifact.model.dto.ProductInfo;
import by.clevertec.checks.demoArtifact.model.dto.SalesReceipt;
import by.clevertec.checks.demoArtifact.model.services.api.IDiscountCardService;
import by.clevertec.checks.demoArtifact.model.services.api.IFinalReceiptService;
import by.clevertec.checks.demoArtifact.model.services.api.ISalesReceiptService;
import by.clevertec.checks.demoArtifact.model.services.api.ValidationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service
public class FinalReceiptService implements IFinalReceiptService {
    private final IFinalReceiptStorage finalReceiptStorage;
    private final ISalesReceiptService salesReceiptService;
    private final IDiscountCardService discountCardService;

    private final ConversionService conversionService;

    public FinalReceiptService(IFinalReceiptStorage finalReceiptStorage,
                               ISalesReceiptService salesReceiptService,
                               IDiscountCardService discountCardService,
                               ConversionService conversionService) {
        this.finalReceiptStorage = finalReceiptStorage;
        this.salesReceiptService = salesReceiptService;
        this.discountCardService = discountCardService;
        this.conversionService = conversionService;
    }

    @Transactional
    @Override
    public FinalReceipt saveAll(UUID idDiscountCard, ProductInfo productInfo) {
        UUID id = UUID.randomUUID();
        List<SalesReceipt> salesReceipts = salesReceiptService.saveAll(id, productInfo);
        List<Double> subtotalAndTotal = salesReceiptService.getGrandTotal(salesReceipts);
        Double subtotal = subtotalAndTotal.get(0);
        Double rawTotal = subtotalAndTotal.get(1);
        Integer discount = discountCardService.getDiscount(idDiscountCard);
        Double total = rawTotal * (1 - (discount * 0.01));

        FinalReceipt finalReceipt = FinalReceipt.Builder.createBuilder()
                .setId(id)
                .setDiscountCard(idDiscountCard)
                .setSubtotal(subtotal)
                .setTotal(total)
                .setDtCreate(LocalDateTime.now())
                .build();

        FinalReceiptEntity finalReceiptEntity = conversionService.convert(finalReceipt, FinalReceiptEntity.class);
        finalReceiptStorage.save(finalReceiptEntity);

        return finalReceipt;
    }

    @Override
    public FinalReceipt get(UUID id) {
        if (!finalReceiptStorage.existsById(id)){
            throw new ValidationException("Не найдено такой карты с Id");
        }
        FinalReceiptEntity finalReceiptEntity  = finalReceiptStorage.getReferenceById(id);
        return conversionService.convert(finalReceiptEntity, FinalReceipt.class);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!finalReceiptStorage.existsById(id)){
            throw new ValidationException("Не найдено такой карты с Id");
        }
        salesReceiptService.deleteAllByIdFinalReceipt(id);
        finalReceiptStorage.deleteById(id);
    }
}
