package by.clevertec.checks.demoArtifact.controller.web.controllers.rest;

import by.clevertec.checks.demoArtifact.model.dto.DiscountCard;
import by.clevertec.checks.demoArtifact.model.dto.ListDiscountCard;
import by.clevertec.checks.demoArtifact.model.dto.Product;
import by.clevertec.checks.demoArtifact.model.services.api.IDiscountCardService;
import by.clevertec.checks.demoArtifact.model.services.api.MessageError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/discount")
public class RestDiscountCardController {
    private final IDiscountCardService discountCardService;

    public RestDiscountCardController(IDiscountCardService discountCardService) {
        this.discountCardService = discountCardService;
    }

    @GetMapping(value = {"get/{uuidOrBarcode}", "/get/{uuidOrBarcode}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public DiscountCard index(@PathVariable String uuidOrBarcode) {
        DiscountCard discountCard;

        try {
            int barcode = Integer.parseInt(uuidOrBarcode);
            discountCard = discountCardService.getByBarcode(barcode);
        } catch (NumberFormatException e) {
            try {
                UUID id = UUID.fromString(uuidOrBarcode);
                discountCard = discountCardService.getByUuid(id);
            } catch (IllegalArgumentException il) {
                throw new ValidationException("Передан неверный формат данных");
            }
        }
        return discountCard;
    }


    @GetMapping(value = {"getAll", "/getAll"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<DiscountCard> index(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                               @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size) {
        List<DiscountCard> discountCards = discountCardService.getAll();

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), discountCards.size());
        return new PageImpl<>(discountCards.subList(start, end), pageable, discountCards.size());
    }

    @PostMapping(value = {"save", "/save"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountCard create(@RequestBody DiscountCard discountCard) {
        DiscountCard discountCard1 = discountCardService.save(discountCard);
        return discountCard1;
    }

    @PostMapping(value = {"saveAll", "/saveAll"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Page<DiscountCard> create1(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
                                 @RequestBody ListDiscountCard discountCards) {
        List<DiscountCard> discountCards1= discountCardService.saveAll(discountCards);

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), discountCards1.size());
        return new PageImpl<>(discountCards1.subList(start, end), pageable, discountCards1.size());
    }

//    @PostMapping(value = {"saveAllFromExcel", "/saveAllFromExcel"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
//    public Page<Product> create1(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
//                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
//                                 @RequestBody FileInputStream file) {
//        List<Product>  products = productService.saveAllFromExcel(file);
//
//        Pageable pageable = Pageable.ofSize(size).withPage(page);
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), products.size());
//        return new PageImpl<>(products.subList(start, end), pageable, products.size());
//    }

    @PutMapping(value = {"update/{uuidOrBarcode}", "/update/{uuidOrBarcode}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public DiscountCard update(@RequestBody DiscountCard discountCard,
                          @PathVariable String uuidOrBarcode) {

        try {
            int barcode = Integer.parseInt(uuidOrBarcode);
            return discountCardService.updateByBarcode(barcode, discountCard);
        } catch (NumberFormatException e) {
            try {
                UUID id = UUID.fromString(uuidOrBarcode);
                return discountCardService.updateByUuid(id, discountCard);
            } catch (IllegalArgumentException il) {
                throw new ValidationException("Передан неверный формат данных");
            }
        }
    }

    @PutMapping(value = {"saveUpdateAll", "/saveUpdateAll"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<DiscountCard> create3(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
                                 @RequestBody ListDiscountCard discountCards) {

        List<DiscountCard> discountCards1 = discountCardService.updateAndSave(discountCards);

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), discountCards1.size());
        return new PageImpl<>(discountCards1.subList(start, end), pageable, discountCards1.size());
    }

//    @PutMapping(value = {"saveUpdateAllFromExcel", "/saveUpdateAllFromExcel"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    public Page<Product> create3(@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
//                                 @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size,
//                                 @RequestBody FileInputStream file) {
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
    public DiscountCard delete (@PathVariable String uuidOrBarcode){
        DiscountCard discountCard;
        try {
            int barcode = Integer.parseInt(uuidOrBarcode);
            discountCard = discountCardService.deleteByBarcode(barcode);
        } catch (NumberFormatException e) {
            try {
                UUID id = UUID.fromString(uuidOrBarcode);
                discountCard = discountCardService.deleteByUuid(id);
            } catch (IllegalArgumentException il) {
                throw new ValidationException("Передан неверный формат данных");
            }
        }

     return discountCard;
    }
    @DeleteMapping (value = {"deleteAll", "/deleteAll"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<DiscountCard> deleteAll (@RequestParam @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                              @RequestParam @Min(value = 1, message = MessageError.PAGE_SIZE) int size){
        List<DiscountCard> discountCards = discountCardService.getAll();
        discountCardService.deleteAll();
        return discountCards;
    }
}
