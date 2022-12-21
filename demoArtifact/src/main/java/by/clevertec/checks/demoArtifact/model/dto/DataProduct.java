package by.clevertec.checks.demoArtifact.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


public class DataProduct implements Serializable {
    private UUID idProduct;
    private Integer quantity;

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}


