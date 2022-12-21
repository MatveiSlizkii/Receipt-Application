package by.clevertec.checks.demoArtifact.model.dto;

import java.io.Serializable;
import java.util.List;

public class ListDiscountCard implements Serializable {
    private List<DiscountCard> discountCards;

    public ListDiscountCard() {
    }

    public ListDiscountCard(List<DiscountCard> discountCards) {
        this.discountCards = discountCards;
    }

    public List<DiscountCard> getDiscountCards() {
        return discountCards;
    }

    public void setDiscountCards(List<DiscountCard> discountCards) {
        this.discountCards = discountCards;
    }
}
