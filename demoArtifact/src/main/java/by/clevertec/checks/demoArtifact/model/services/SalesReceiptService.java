package by.clevertec.checks.demoArtifact.model.services;

import by.clevertec.checks.demoArtifact.dao.api.ISalesReceiptStorage;
import by.clevertec.checks.demoArtifact.dao.entity.SalesReceiptEntity;
import by.clevertec.checks.demoArtifact.model.dto.DataProduct;
import by.clevertec.checks.demoArtifact.model.dto.ProductInfo;
import by.clevertec.checks.demoArtifact.model.dto.SalesReceipt;
import by.clevertec.checks.demoArtifact.model.services.api.IProductService;
import by.clevertec.checks.demoArtifact.model.services.api.ISalesReceiptService;
import by.clevertec.checks.demoArtifact.model.services.api.InfoStore;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SalesReceiptService implements ISalesReceiptService {
    private final ISalesReceiptStorage salesReceiptStorage;
    private final IProductService productService;

    private final ConversionService conversionService;

    public SalesReceiptService(ISalesReceiptStorage salesReceiptStorage, IProductService productService,
                               ConversionService conversionService) {
        this.salesReceiptStorage = salesReceiptStorage;
        this.productService = productService;
        this.conversionService = conversionService;
    }

    @Override
    public SalesReceipt save(UUID idFinalReceipt, UUID idProduct,
                             Integer quantity) {
        productService.updateQuantityMinus(idProduct, quantity);
        LocalDateTime localDateTime = LocalDateTime.now();

        Double price = productService.getByUuid(idProduct).getCost();
        Double subtotal = price * quantity;
        Double quantityDiscount = 0.0;
        if (quantity >= InfoStore.QUANTITY_FOR_DISCOUNT){
            quantityDiscount = subtotal * (InfoStore.QUANTITY_DISCOUNT * 0.01);
        }

        Double total = subtotal - quantityDiscount;

        SalesReceipt salesReceipt = SalesReceipt.Builder.createBuilder()
                .setId(UUID.randomUUID())
                .setIdFinalReceipt(idFinalReceipt)
                .setIdProduct(idProduct)
                .setQuantity(quantity)
                .setPrice(price)
                .setSubtotal(subtotal)
                .setQuantityDiscount(quantityDiscount)
                .setTotal(total)
                .setDtCreate(localDateTime)
                .build();

        SalesReceiptEntity salesReceiptEntity = conversionService.convert(salesReceipt, SalesReceiptEntity.class);
        salesReceiptStorage.save(salesReceiptEntity);

        return salesReceipt;
    }

    @Override
    public List<SalesReceipt> saveAll(UUID idFinalReceipt, ProductInfo productInfo) {

        List<SalesReceipt> salesReceipts = new ArrayList<>();
        List<DataProduct> products = productInfo.getProductInfoList();
        for (DataProduct dp: products) {
            SalesReceipt salesReceipt = this.save(idFinalReceipt, dp.getIdProduct(), dp.getQuantity());
            salesReceipts.add(salesReceipt);
        }

//        for (Map.Entry<UUID, Integer> entry : productInfo.entrySet()) {
//            SalesReceipt salesReceipt = this.save(idFinalReceipt, entry.getKey(), entry.getValue());
//            salesReceipts.add(salesReceipt);
//        }
        return salesReceipts;
    }

    @Override
    public SalesReceipt get(UUID id) {
        SalesReceiptEntity salesReceiptEntity = salesReceiptStorage.getReferenceById(id);

        return conversionService.convert(salesReceiptEntity, SalesReceipt.class);
    }

    @Override
    public List<SalesReceipt> getAllByIdFinalReceipt(UUID idFinalReceipt) {
        List<SalesReceiptEntity> salesReceiptEntities = salesReceiptStorage.findAllByIdFinalReceipt(idFinalReceipt);
        List<SalesReceipt> salesReceipts = new ArrayList<>();
        salesReceiptEntities.forEach((o)-> salesReceipts.add(conversionService.convert(o, SalesReceipt.class)));
        return salesReceipts;
    }

    @Override
    public List<Double>  getGrandTotal(List<SalesReceipt> salesReceipts) {
        Double grandTotal = 0.0;
        Double subtotal = 0.0;
        for (SalesReceipt s: salesReceipts) {
            Double total = s.getTotal();
            grandTotal += total;
            Double subtotal1 = s.getSubtotal();
            subtotal += subtotal1;
        }
        List<Double> doubles = new ArrayList<>();
        doubles.add(subtotal);
        doubles.add(grandTotal);
        return doubles;
    }

    @Transactional
    @Override
    public void deleteAllByIdFinalReceipt(UUID idFinalReceipt) {
        List<SalesReceiptEntity> salesReceiptEntities = salesReceiptStorage.findAllByIdFinalReceipt(idFinalReceipt);
        Map<UUID, Integer> uuidProductAndQuantity = new HashMap<>();
        salesReceiptEntities.forEach((o)-> uuidProductAndQuantity.put(o.getIdProduct(), o.getQuantity()));

        for (Map.Entry<UUID, Integer> entry : uuidProductAndQuantity.entrySet()) {
            productService.updateQuantityPlus(entry.getKey(), entry.getValue());
        }
    }
}
