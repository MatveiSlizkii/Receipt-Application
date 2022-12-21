package by.clevertec.checks.demoArtifact.dao.converters;

import by.clevertec.checks.demoArtifact.dao.entity.SalesReceiptEntity;
import by.clevertec.checks.demoArtifact.model.dto.SalesReceipt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SalesReceiptConverter implements Converter<SalesReceiptEntity, SalesReceipt> {


    @Override
    public SalesReceipt convert(SalesReceiptEntity source) {

        return SalesReceipt.Builder.createBuilder()
                .setId(source.getId())
                .setIdFinalReceipt(source.getIdFinalReceipt())
                .setIdProduct(source.getIdProduct())
                .setPrice(source.getPrice())
                .setQuantity(source.getQuantity())
                .setSubtotal(source.getSubtotal())
                .setTotal(source.getTotal())
                .setQuantityDiscount(source.getQuantityDiscount())
                .setDtCreate(source.getDtCreate())
                .build();
    }

    @Override
    public <U> Converter<SalesReceiptEntity, U> andThen(Converter<? super SalesReceipt, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}






