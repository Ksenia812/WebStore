package com.ksenia.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksenia.demo.model.ProductType;

import java.io.IOException;
import java.util.Set;

public class JsonConverter {
    public static String toJSON(Set<ProductType> productTypes) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(productTypes);
    }
}
