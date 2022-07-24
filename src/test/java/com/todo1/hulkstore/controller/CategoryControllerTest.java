package com.todo1.hulkstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo1.hulkstore.entity.CategoryEntity;
import com.todo1.hulkstore.service.ICategoryService;
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
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ICategoryService categoryService;

    @Test
    void getAllCategories() throws Exception {
        List<CategoryEntity> categoryList = new ArrayList<CategoryEntity>();
        categoryList.add(new CategoryEntity("Camisetas", true));
        categoryList.add(new CategoryEntity("Vasos", true));
        categoryList.add(new CategoryEntity("Juguetes", true));
        categoryList.add(new CategoryEntity("Accesorios Marvel", true));
        categoryList.add(new CategoryEntity("Accesorios DC", true));
        categoryList.add(new CategoryEntity("Alternativos", true));
        when(categoryService.getAllCategories()).thenReturn(categoryList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(6))).andDo(print());
    }

    @Test
    void saveCategorySuccess() throws Exception {
        CategoryEntity category = new CategoryEntity("Camisetas", true);
        ObjectMapper objectMapper = new ObjectMapper();
        String categoryJson = objectMapper.writeValueAsString(category);

        when(categoryService.save(any(CategoryEntity.class))).thenReturn(category);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Camisetas"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void updateCategorySuccess() throws Exception {
        CategoryEntity category = new CategoryEntity("Camisetas", false);
        ObjectMapper objectMapper = new ObjectMapper();
        String categoryJson = objectMapper.writeValueAsString(category);

        when(categoryService.update(any(CategoryEntity.class))).thenReturn(category);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Camisetas"))
                .andExpect(jsonPath("$.active").value(false));
    }
}
