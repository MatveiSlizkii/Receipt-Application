package by.clevertec.checks.demoArtifact.config;

import by.clevertec.checks.demoArtifact.dao.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {



    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ProductConverter());
        registry.addConverter(new ProductEntityConverter());
        registry.addConverter(new LongToLocalDateTimeConverter());

        registry.addConverter(new DiscountCardConverter());
        registry.addConverter(new DiscountCardEntityConverter());

        registry.addConverter(new SalesReceiptConverter());
        registry.addConverter(new SalesReceiptEntityConverter());

    }
}
