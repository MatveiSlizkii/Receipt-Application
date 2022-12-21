package by.clevertec.checks.demoArtifact.model.dto;

import java.io.Serializable;
import java.util.List;

public class ListProduct implements Serializable {
    private List<Product> products;

    public ListProduct() {
    }

    public ListProduct(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
