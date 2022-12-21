package by.clevertec.checks.demoArtifact.model.services;

import by.clevertec.checks.demoArtifact.dao.api.IProductStorage;
import by.clevertec.checks.demoArtifact.dao.entity.ProductEntity;
import by.clevertec.checks.demoArtifact.model.dto.ListProduct;
import by.clevertec.checks.demoArtifact.model.dto.Product;
import by.clevertec.checks.demoArtifact.model.services.api.IProductService;
import by.clevertec.checks.demoArtifact.model.services.api.MessageError;
import by.clevertec.checks.demoArtifact.model.services.api.ValidationError;
import by.clevertec.checks.demoArtifact.model.services.api.ValidationException;
import jakarta.persistence.EntityManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductService {
    private final IProductStorage productStorage;
    private final ConversionService conversionService;
    private final EntityManager em;

    public ProductService(IProductStorage productStorage, ConversionService conversionService, EntityManager em) {
        this.productStorage = productStorage;
        this.conversionService = conversionService;
        this.em = em;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        productStorage.findAll().forEach((o) -> products.add(conversionService.convert(o, Product.class)));
        return products;
    }

    @Override
    public Product getByUuid(UUID id) {
        ProductEntity productEntity;
        if (productStorage.existsById(id)) {
            productEntity = productStorage.getReferenceById(id);
        } else {
            throw new ValidationException("Передано несуществующее Id");
        }
        return conversionService.convert(productEntity, Product.class);
    }

    @Override
    public Product getByBarcode(int barcode) {
        ProductEntity productEntity;
        if (productStorage.existsByBarcode(barcode)) {
            productEntity = productStorage.findByBarcode(barcode);
        } else {
            throw new ValidationException("Передано несуществубщее поле Barcode");
        }
        return conversionService.convert(productEntity, Product.class);
    }

    @Override
    public Product save(Product product) {

        LocalDateTime localDateTime = LocalDateTime.now();

        List<ValidationError> errors = new ArrayList<>();

        //Проверка есть ли данный товар через штрихкод
        errors = checkBarcode(errors, product);
        errors = checkProduct(errors, product);

        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }
        product.setId(UUID.randomUUID());
        product.setDtCreate(localDateTime);
        product.setDtUpdate(localDateTime);
        ProductEntity productEntity = conversionService.convert(product, ProductEntity.class);
        productStorage.save(productEntity);

        return product;
    }


    @Override
    public List<Product> saveAll(ListProduct products) {


        List<ValidationError> errors = new ArrayList<>();
        errors = checkListProducts(errors, products.getProducts());
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }

        List<Product> products1 = new ArrayList<>();
        for (Product product : products.getProducts()) {
            if (!productStorage.existsByBarcode(product.getBarcode())) {
                Product product1 = this.save(product);
                products1.add(product1);
            }  else {
                errors.add(new ValidationError("Не сохранена сущность со штрихкодом: " + product.getBarcode(),
                        MessageError.SQL_ERROR));
            }
        }
        return products1;
    }

    @Override
    @Transactional
    public Product updateByUuid(UUID id, Product rawProduct) {
        List<ValidationError> errors = new ArrayList<>();

        //Проверка есть ли данный товар через штрихкод
        errors = checkProduct(errors, rawProduct);

        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }
        if (!productStorage.existsById(id)){
            throw new ValidationException(MessageError.ID_NOT_EXIST);
        }
        ProductEntity productEntity = em.find(ProductEntity.class, id);
        productEntity.setBarcode(rawProduct.getBarcode());
        productEntity.setCost(rawProduct.getCost());
        productEntity.setName(rawProduct.getName());
        productEntity.setQuantity(rawProduct.getQuantity());

        return conversionService.convert(productEntity, Product.class);
    }

    @Override
    @Transactional
    public Product updateByBarcode(int barcode, Product rawProduct) {
        List<ValidationError> errors = new ArrayList<>();

        //Проверка есть ли данный товар через штрихкод
        errors = checkProduct(errors, rawProduct);

        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }
        if (!productStorage.existsByBarcode(barcode)){
            throw new ValidationException(MessageError.ID_NOT_EXIST);
        }
        UUID id = productStorage.findByBarcode(barcode).getId();

        ProductEntity productEntity = em.find(ProductEntity.class, id);

        productEntity.setBarcode(rawProduct.getBarcode());
        productEntity.setCost(rawProduct.getCost());
        productEntity.setName(rawProduct.getName());
        productEntity.setQuantity(rawProduct.getQuantity());


        return conversionService.convert(productEntity, Product.class);
    }

    @Override
    public Product updateQuantityMinus(UUID id, Integer quantity) {
        if (!productStorage.existsById(id)){
            throw new ValidationException(MessageError.ID_NOT_EXIST);
        }
        ProductEntity productEntity = em.find(ProductEntity.class, id);

        if (productEntity.getQuantity() < quantity){
            throw new ValidationException("Данного количества нет в наличии, в наличии: " + productEntity.getQuantity());
        }
        productEntity.setQuantity(productEntity.getQuantity() - quantity);

        return conversionService.convert(productEntity, Product.class);
    }

    @Override
    public Product updateQuantityPlus(UUID id, Integer quantity) {
        if (!productStorage.existsById(id)){
            throw new ValidationException(MessageError.ID_NOT_EXIST);
        }

        ProductEntity productEntity = em.find(ProductEntity.class, id);

        productEntity.setQuantity(productEntity.getQuantity() + quantity);

        return conversionService.convert(productEntity, Product.class);
    }

    @Override
    public List<Product> updateAndSave(ListProduct products1) {
        List<ValidationError> errors = new ArrayList<>();
        List<Product> products = products1.getProducts();

        errors = checkListProducts(errors, products);
        if (!errors.isEmpty()) {
            throw new ValidationException("Переданы некорректные параметры", errors);
        }
        List<Product> products2 = new ArrayList<>();
        for (Product product : products) {

            if (productStorage.existsByBarcode(product.getBarcode())) {
                products.add(this.updateByBarcode(product.getBarcode(), product));
            } else {
                products.add(this.save(product));
            }
        }
        return products2;
    }

//    @Override
//    public List<Product> saveAllFromExcel(MultipartFile file) {
//        List<Product> products = this.readExcel(file);
//        List<Product> products1 = this.saveAll(products);
//        return products1;
//    }
//
//    @Override
//    public List<Product> updateAndSaveFromExcel(MultipartFile file) {
//        List<Product> products = this.readExcel(file);
//        return this.updateAndSave(products);
//    }

    @Override
    public Product deleteByUuid(UUID id) {
        ProductEntity productEntity = productStorage.getReferenceById(id);
        productStorage.deleteById(id);

        return conversionService.convert(productEntity, Product.class);
    }

    @Override
    public Product deleteByBarcode(int barcode) {
        ProductEntity productEntity = productStorage.findByBarcode(barcode);
        productStorage.deleteByBarcode(barcode);
        return conversionService.convert(productEntity, Product.class);
    }

    @Override
    public List<Product> deleteAll() {
        List<ProductEntity> productEntities = productStorage.findAll();
        productStorage.deleteAll();
        productStorage.deleteAll();
        List<Product> products = new ArrayList<>();
        productEntities.forEach((o) -> products.add(conversionService.convert(o, Product.class)));
        return products;
    }

    private List<ValidationError> checkBarcode(List<ValidationError> errors, Product product) {

        if (productStorage.existsByBarcode(product.getBarcode())) {
            errors.add(new ValidationError("barcode: " + product.getBarcode().toString(), MessageError.NO_UNIQUE_FIELD));
        }
        return errors;
    }


    private List<ValidationError> checkProduct(List<ValidationError> errors, Product product) {
        if (product.getName() == null) {
            errors.add(new ValidationError("name", MessageError.MISSING_FIELD));
        }
        if (product.getBarcode() == null) {
            errors.add(new ValidationError("barcode", MessageError.MISSING_FIELD));
        }
        if (product.getCost() == null) {
            errors.add(new ValidationError("cost", MessageError.MISSING_FIELD));
        }
        if (product.getQuantity() == null) {
            errors.add(new ValidationError("quantity", MessageError.MISSING_FIELD));
        }
        return errors;
    }

    private List<ValidationError> checkListProducts(List<ValidationError> errors, List<Product> products) {
        for (Product product : products) {
            this.checkProduct(errors, product);
        }
        return errors;
    }

    private List<Product> readExcel(MultipartFile file) {


        List<Product> products = new ArrayList<>();
        try {
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = (XSSFWorkbook) file;

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            boolean finish = false;
            int tmp = 1;
            while (!finish) {
                System.out.println(tmp);
                try {
                    sheet.getRow(tmp).getCell(0).getRawValue();
                } catch (NullPointerException e) {
                    break;
                }
                Product product = Product.Builder.createBuilder()
                        .setBarcode(Integer.parseInt(sheet.getRow(tmp).getCell(0).getRawValue()))
                        .setName(sheet.getRow(tmp).getCell(1).getRawValue())
                        .setQuantity(Integer.parseInt(sheet.getRow(tmp).getCell(2).getRawValue()))
                        .setCost(Double.parseDouble(sheet.getRow(tmp).getCell(3).getRawValue()))
                        .build();
                products.add(product);
                tmp++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Невозможно прочитать файл");
        }
        return products;
    }


}
