package by.clevertec.checks.demoArtifact.dao.converters;

import by.clevertec.checks.demoArtifact.dao.entity.SalesReceiptEntity;
import by.clevertec.checks.demoArtifact.model.dto.SalesReceipt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SalesReceiptEntityConverter implements Converter<SalesReceipt, SalesReceiptEntity> {


    @Override
    public SalesReceiptEntity convert(SalesReceipt source) {

        return SalesReceiptEntity.Builder.createBuilder()
                .setId(source.getId())
                .setIdFinalReceipt(source.getIdFinalReceipt())
                .setIdProduct(source.getIdProduct())
                .setQuantity(source.getQuantity())
                .setPrice(source.getPrice())
                .setSubtotal(source.getSubtotal())
                .setTotal(source.getTotal())
                .setQuantityDiscount(source.getQuantityDiscount())
                .setDtCreate(source.getDtCreate())
                .build();
    }

    @Override
    public <U> Converter<SalesReceipt, U> andThen(Converter<? super SalesReceiptEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}






