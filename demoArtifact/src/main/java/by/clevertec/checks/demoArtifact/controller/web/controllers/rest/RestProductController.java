package by.clevertec.checks.demoArtifact.controller.web.controllers.rest;

import by.clevertec.checks.demoArtifact.model.dto.ListProduct;
import by.clevertec.checks.demoArtifact.model.dto.Product;
import by.clevertec.checks.demoArtifact.model.services.api.IProductService;
import by.clevertec.checks.demoArtifact.model.services.api.MessageError;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/product")
public class RestProductController {
    private final IProductService productService;

    public RestProductController(IProductService productService) {
        this.productService = productService;
    }


    @GetMapping(value = {"get/{uuidOrBarcode}", "/get/{uuidOrBarcode}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Product index(@PathVariable String uuidOrBarcode) {
        Product product;

        try {
            int barcode = Integer.parseInt(uuidOrBarcode);
            product = productService.getByBarcode(barcode);
        } catch (NumberFormatException e) {
            try {
                UUID id = UUID.fromString(uuidOrBarcode);
                product = productService.getByUuid(id);
            } catch (IllegalArgumentException il) {
                throw new ValidationException("Передан неверный формат данных");
                //TODO добавить ворнинг
            }
        }
        return product;
    }


    @GetMapping(value = {"getAll", "/getAll"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> index(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                               @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size) {
        List<Product> products = productService.getAll();

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products.size());
        return new PageImpl<>(products.subList(start, end), pageable, products.size());
    }

    @PostMapping(value = {"save", "/save"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        Product product1 = productService.save(product);
        return product1;
    }

    @PostMapping(value = {"saveAll", "/saveAll"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Page<Product> create1(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
                                 @RequestBody ListProduct products) {
        List<Product> products1= productService.saveAll(products);

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products1.size());
        return new PageImpl<>(products1.subList(start, end), pageable, products1.size());
    }

//    @PostMapping(value = {"saveAllFromExcel", "/saveAllFromExcel"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
//    public Page<Product> create1(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
//                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
//                                 @RequestBody FileInputStream file) {
//        System.out.println("dfdsf");
//        List<Product>  products = productService.saveAllFromExcel(file);
//
//        Pageable pageable = Pageable.ofSize(size).withPage(page);
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), products.size());
//        return new PageImpl<>(products.subList(start, end), pageable, products.size());
//   return null;
//    }

    @PutMapping(value = {"update/{uuidOrBarcode}", "/update/{uuidOrBarcode}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Product update(@RequestBody Product product,
                          @PathVariable String uuidOrBarcode) {

        try {
            int barcode = Integer.parseInt(uuidOrBarcode);
            return productService.updateByBarcode(barcode, product);
        } catch (NumberFormatException e) {
            try {
                UUID id = UUID.fromString(uuidOrBarcode);
                return productService.updateByUuid(id, product);
            } catch (IllegalArgumentException il) {
                throw new ValidationException("Передан неверный формат данных");
                //TODO добавить ворнинг
            }
        }
    }

    @PutMapping(value = {"saveUpdateAll", "/saveUpdateAll"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> create3(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
                                 @RequestBody ListProduct products) {

        List<Product> products1 = productService.updateAndSave(products);

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products1.size());
        return new PageImpl<>(products1.subList(start, end), pageable, products1.size());
    }

//    @PutMapping(value = {"saveUpdateAllFromExcel", "/saveUpdateAllFromExcel"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    public Page<Product> create3(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
//                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
//                                 @RequestBody MultipartFile file) {
//
//        List<Product> products = productService.updateAndSaveFromExcel(file);
//        Pageable pageable = Pageable.ofSize(size).withPage(page);
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), products.size());
//        return new PageImpl<>(products.subList(start, end), pageable, products.size());
//    }

    @DeleteMapping (value = {"delete/{uuidOrBarcode}", "/delete/{uuidOrBarcode}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Product delete (@PathVariable String uuidOrBarcode){
        Product product;
        try {
            int barcode = Integer.parseInt(uuidOrBarcode);
            product = productService.deleteByBarcode(barcode);
        } catch (NumberFormatException e) {
            try {
                UUID id = UUID.fromString(uuidOrBarcode);
                product = productService.deleteByUuid(id);
            } catch (IllegalArgumentException il) {
                throw new ValidationException("Передан неверный формат данных");
                //TODO добавить ворнинг
            }
        }

     return product;
    }
    @DeleteMapping (value = {"deleteAll", "/deleteAll"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Product> deleteAll (@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                              @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size){
        List<Product> products = productService.getAll();
        productService.deleteAll();
        return products;
    }
}
