package com.theverygroup.products.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theverygroup.products.dto.Product;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

public final class ProductDataUtils {

    private static final String RESOURCE_LOCATION = "src/main/resources/data/products.json";

    @SneakyThrows
    public static List<Product> loadAll() {
        String contents = FileUtils.readFileToString(ResourceUtils.getFile(RESOURCE_LOCATION), StandardCharsets.UTF_8);
        return new ObjectMapper().readValue(contents, new TypeReference<>() {
        });
    }
}