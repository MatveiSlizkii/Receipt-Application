package by.clevertec.checks.demoArtifact.model.services.api;

import by.clevertec.checks.demoArtifact.model.dto.ListProduct;
import by.clevertec.checks.demoArtifact.model.dto.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

public interface IProductService {
        /*
        Сервис для работы с товарами на складе
         */

        List<Product> getAll ();
        Product getByUuid(UUID id);
        Product getByBarcode(int barcode);
        Product save (Product product);
        List<Product> saveAll (ListProduct products);
        //List<Product> saveAllFromExcel (MultipartFile file);
        //List<Product> reSaveAllFromExcel (FileInputStream file);
        Product updateByUuid(UUID id, Product rawProduct);
        Product updateByBarcode(int barcode, Product rawProduct);
        List<Product> updateAndSave (ListProduct products);
        //List<Product> updateAndSaveFromExcel(MultipartFile file);

        Product updateQuantityMinus(UUID id, Integer quantity);
        Product updateQuantityPlus (UUID id, Integer quantity);
        Product deleteByBarcode(int barcode);
        Product deleteByUuid (UUID id);
        List<Product> deleteAll();






}
