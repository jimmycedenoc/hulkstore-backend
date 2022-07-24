package com.todo1.hulkstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.service.IProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WithMockUser
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Test
    void getAllProducts() throws Exception {
        List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
        productEntityList.add(new ProductEntity("Camisa rosada", 100L, 1L, 10.00));
        productEntityList.add(new ProductEntity("Camisa negra", 100L, 1L, 15.00));
        when(productService.getAllProducts()).thenReturn(productEntityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void saveProductSuccess() throws Exception {
        ProductEntity product = new ProductEntity("Camisa rosada", 100L, 1L, 10.00);
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);

        when(productService.save(any(ProductEntity.class))).thenReturn(product);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Camisa rosada"))
                .andExpect(jsonPath("$.stock").value(100L))
                .andExpect(jsonPath("$.categoryCode").value(1L))
                .andExpect(jsonPath("$.unitValue").value(10.00));
    }

    @Test
    void updateProductSuccess() throws Exception {
        ProductEntity product = new ProductEntity("Camisa rosada", 100L, 1L, 10.00);
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);

        when(productService.update(any(ProductEntity.class))).thenReturn(product);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Camisa rosada"))
                .andExpect(jsonPath("$.stock").value(100L))
                .andExpect(jsonPath("$.categoryCode").value(1L))
                .andExpect(jsonPath("$.unitValue").value(10.00));
    }
}
