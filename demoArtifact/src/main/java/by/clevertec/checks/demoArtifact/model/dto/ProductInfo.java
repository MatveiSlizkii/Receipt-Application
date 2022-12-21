package by.clevertec.checks.demoArtifact.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


public class ProductInfo implements Serializable {

    private List<DataProduct> productInfoList;

    public ProductInfo() {
    }

    public ProductInfo(List productInfoList) {
        this.productInfoList = productInfoList;
    }

    public List getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<DataProduct> productInfoList) {
        this.productInfoList = productInfoList;
    }
}
