package by.clevertec.checks.demoArtifact.model.dto;

public class ProductsForCustomer {
    private String name;
    private Integer quantity;
    private Double cost;
    private Double subtotal;
    private Double totalWithQuantityDiscount;

    public ProductsForCustomer(String name, Integer quantity, Double cost,
                               Double subtotal, Double totalWithQuantityDiscount) {
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
        this.subtotal = subtotal;
        this.totalWithQuantityDiscount = totalWithQuantityDiscount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotalWithQuantityDiscount() {
        return totalWithQuantityDiscount;
    }

    public void setTotalWithQuantityDiscount(Double totalWithQuantityDiscount) {
        this.totalWithQuantityDiscount = totalWithQuantityDiscount;
    }

    public static class Builder {
        private String name;
        private Integer quantity;
        private Double cost;
        private Double subtotal;
        private Double totalWithQuantityDiscount;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setCost(Double cost) {
            this.cost = cost;
            return this;
        }

        public Builder setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder setTotalWithQuantityDiscount(Double totalWithQuantityDiscount) {
            this.totalWithQuantityDiscount = totalWithQuantityDiscount;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public ProductsForCustomer build() {
            return new ProductsForCustomer(name, quantity, cost, subtotal, totalWithQuantityDiscount);
        }
    }
}
