package by.clevertec.checks.demoArtifact.model.services;

import by.clevertec.checks.demoArtifact.dao.api.IDiscountCardStorage;
import by.clevertec.checks.demoArtifact.dao.entity.DiscountCardEntity;
import by.clevertec.checks.demoArtifact.model.dto.DiscountCard;
import by.clevertec.checks.demoArtifact.model.dto.ListDiscountCard;
import by.clevertec.checks.demoArtifact.model.dto.Product;
import by.clevertec.checks.demoArtifact.model.services.api.IDiscountCardService;
import by.clevertec.checks.demoArtifact.model.services.api.MessageError;
import by.clevertec.checks.demoArtifact.model.services.api.ValidationError;
import jakarta.persistence.EntityManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DiscountCardService implements IDiscountCardService {
    private final IDiscountCardStorage discountCardStorage;
    private final EntityManager em;
    private final ConversionService conversionService;

    public DiscountCardService(IDiscountCardStorage discountCardStorage, EntityManager em, ConversionService conversionService) {
        this.discountCardStorage = discountCardStorage;
        this.em = em;
        this.conversionService = conversionService;
    }

    @Override
    public DiscountCard getByUuid(UUID id) {
        DiscountCardEntity discountCardEntity;
        if (discountCardStorage.existsById(id)) {
            discountCardEntity = discountCardStorage.getReferenceById(id);
        } else {
            throw new ValidationException("Передан несуществующий Id");
        }

        return conversionService.convert(discountCardEntity, DiscountCard.class);
    }

    @Override
    public DiscountCard getByBarcode(Integer barcode) {
        DiscountCardEntity discountCardEntity;
        if (discountCardStorage.existsByBarcode(barcode)) {
            discountCardEntity = discountCardStorage.findByBarcode(barcode);
        } else {
            throw new ValidationException("Передано несуществующий Barcode");
        }
        return conversionService.convert(discountCardEntity, DiscountCard.class);
    }

    @Override
    public List<DiscountCard> getAll() {
        List<DiscountCardEntity> discountCardEntities = discountCardStorage.findAll();
        List<DiscountCard> discountCards = new ArrayList<>();
        discountCardEntities.forEach((o) -> discountCards.add(conversionService.convert(o, DiscountCard.class)));
        return discountCards;
    }

    @Override
    public DiscountCard save(DiscountCard discountCard) {
        List<ValidationError> errors = new ArrayList<>();

        errors = this.checkDiscountCard(errors, discountCard);
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", (Throwable) errors);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        discountCard.setId(UUID.randomUUID());
        discountCard.setDtCreate(localDateTime);
        discountCard.setDtUpdate(localDateTime);

        DiscountCardEntity discountCardEntity = conversionService.convert(discountCard, DiscountCardEntity.class);
        DiscountCardEntity discountCardEntity1 = discountCardStorage.save(discountCardEntity);
        return conversionService.convert(discountCardEntity1, DiscountCard.class);
    }

    @Override
    public List<DiscountCard> saveAll(ListDiscountCard discountCards) {
        List<ValidationError> errors = new ArrayList<>();

        List<DiscountCard> discountCards2 = discountCards.getDiscountCards();

        errors = checkListDiscountCard(errors, discountCards2);
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", (Throwable) errors);
        }
        List<DiscountCard> discountCards1 = new ArrayList<>();

        for (DiscountCard discountCard : discountCards2) {
            if (!discountCardStorage.existsByBarcode(discountCard.getBarcode())) {
                DiscountCard discountCard1 = this.save(discountCard);
                discountCards1.add(discountCard1);
            } else {
                errors.add(new ValidationError("Не сохранена сущность со штрихкодом: " + discountCard.getBarcode(),
                        MessageError.SQL_ERROR));
            }
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", (Throwable) errors);
        }
        return discountCards1;
    }

    @Override
    public DiscountCard updateByUuid(UUID id, DiscountCard discountCard) {
        List<ValidationError> errors = new ArrayList<>();

        errors = this.checkDiscountCard(errors, discountCard);
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", (Throwable) errors);
        }

        DiscountCardEntity discountCardEntity = em.find(DiscountCardEntity.class, id);

        discountCardEntity.setBarcode(discountCard.getBarcode());
        discountCardEntity.setDiscount(discountCard.getDiscount());

        return conversionService.convert(discountCardEntity, DiscountCard.class);
    }

    @Override
    public DiscountCard updateByBarcode(Integer barcode, DiscountCard discountCard) {
        List<ValidationError> errors = new ArrayList<>();

        errors = this.checkDiscountCard(errors, discountCard);
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", (Throwable) errors);
        }

        UUID id = discountCardStorage.findByBarcode(barcode).getId();
        DiscountCardEntity discountCardEntity = em.find(DiscountCardEntity.class, id);

        discountCardEntity.setBarcode(discountCard.getBarcode());
        discountCardEntity.setDiscount(discountCard.getDiscount());

        return conversionService.convert(discountCardEntity, DiscountCard.class);
    }

    @Override
    public List<DiscountCard>  updateAndSave(ListDiscountCard discountCards) {
        List<DiscountCard> discountCards2 = discountCards.getDiscountCards();

        List<ValidationError> errors = new ArrayList<>();
        errors = checkListDiscountCard(errors, discountCards2);

        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", (Throwable) errors);
        }
        List<DiscountCard> discountCards1 = new ArrayList<>();
        for (DiscountCard discountCard : discountCards2) {

            if (discountCardStorage.existsByBarcode(discountCard.getBarcode())) {
                discountCards1.add(this.updateByBarcode(discountCard.getBarcode(), discountCard));
            } else {
                discountCards1.add(this.save(discountCard));

            }
        }

        return discountCards1;
    }

    @Override
    public DiscountCard deleteByUuid(UUID id) {
        if (!discountCardStorage.existsById(id)) {
            throw new ValidationException("передан несуществующий Id");
        }
        DiscountCardEntity discountCardEntity = discountCardStorage.getReferenceById(id);
        discountCardStorage.deleteById(id);
        return conversionService.convert(discountCardEntity, DiscountCard.class);
    }

    @Override
    public DiscountCard deleteByBarcode(Integer barcode) {

        if (!discountCardStorage.existsByBarcode(barcode)) {
            throw new ValidationException("передан несуществующий barcode");
        }

        DiscountCardEntity discountCardEntity = discountCardStorage.findByBarcode(barcode);
        discountCardStorage.deleteById(discountCardEntity.getId());
        return conversionService.convert(discountCardEntity, DiscountCard.class);
    }

    @Override
    public List<DiscountCard> deleteAll() {
        List<DiscountCardEntity> discountCardEntities = discountCardStorage.findAll();
        discountCardStorage.deleteAll();
        List<DiscountCard> discountCards = new ArrayList<>();
        discountCardEntities.forEach((o) -> discountCards.add(conversionService.convert(o, DiscountCard.class)));
        return discountCards;
    }

    private List<ValidationError> checkDiscountCard(List<ValidationError> errors, DiscountCard discountCard) {
        if (discountCard.getDiscount() == null) {
            errors.add(new ValidationError("quantity", MessageError.MISSING_FIELD));
        }
        if (discountCard.getBarcode() == null) {
            errors.add(new ValidationError("barcode", MessageError.MISSING_FIELD));
        }
        return errors;
    }
    private List<ValidationError> checkListDiscountCard (List<ValidationError> errors, List<DiscountCard> discountCards){
        for (DiscountCard discount: discountCards) {
            errors = this.checkDiscountCard(errors, discount);
        }
        return errors;
    }

    @Override
    public Integer getDiscount(UUID id) {

        DiscountCard discountCard = this.getByUuid(id);

        return discountCard.getDiscount();
    }
}
