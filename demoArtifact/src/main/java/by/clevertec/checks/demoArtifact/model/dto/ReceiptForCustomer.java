package by.clevertec.checks.demoArtifact.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReceiptForCustomer {
    private LocalDateTime localDateTime;
    private List<ProductsForCustomer> products;
    private Double subtotal;
    private Double total;

    public ReceiptForCustomer(LocalDateTime localDateTime, List<ProductsForCustomer> products,
                              Double subtotal, Double total) {
        this.localDateTime = localDateTime;
        this.products = products;
        this.subtotal = subtotal;
        this.total = total;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public List<ProductsForCustomer> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsForCustomer> products) {
        this.products = products;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    public static class Builder {
        private LocalDateTime localDateTime;
        private List<ProductsForCustomer> products;
        private Double subtotal;
        private Double total;

        public Builder() {
        }

        public Builder setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
            return this;
        }

        public Builder setProducts(List<ProductsForCustomer> products) {
            this.products = products;
            return this;
        }

        public Builder setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder setTotal(Double total) {
            this.total = total;
            return this;
        }
        public static Builder createBuilder() {
            return new Builder();
        }

        public ReceiptForCustomer build() {
            return new ReceiptForCustomer(localDateTime, products, subtotal, total);
        }
    }
}
