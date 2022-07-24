package com.todo1.hulkstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo1.hulkstore.entity.InventoryEntity;
import com.todo1.hulkstore.service.IInventoryService;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WithMockUser
@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IInventoryService inventoryService;

    @Test
    void getAllInventories() throws Exception {
        List<InventoryEntity> inventoryEntityList = new ArrayList<InventoryEntity>();
        inventoryEntityList.add(new InventoryEntity(new Date(), 2L, 1L, 1L, 10.25,"Compra"));
        inventoryEntityList.add(new InventoryEntity(new Date(), 2L, 1L, 1L, 10.00, "Venta"));
        when(inventoryService.getAllInventories()).thenReturn(inventoryEntityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void saveInventorySuccess() throws Exception {
        InventoryEntity inventory = new InventoryEntity(new Date(), 2L, 1L, 1L, 10.25, "Compra");
        ObjectMapper objectMapper = new ObjectMapper();
        String inventoryJson = objectMapper.writeValueAsString(inventory);

        when(inventoryService.save(any(InventoryEntity.class))).thenReturn(inventory);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(2L))
                .andExpect(jsonPath("$.saleCode").value(1L))
                .andExpect(jsonPath("$.productCode").value(1L))
                .andExpect(jsonPath("$.unitValue").value(10.25))
                .andExpect(jsonPath("$.detail").value("Compra"));
    }

    @Test
    void updateInventorySuccess() throws Exception {
        InventoryEntity inventory = new InventoryEntity(new Date(), 2L, 1L, 1L, 10.25, "Compra");
        ObjectMapper objectMapper = new ObjectMapper();
        String inventoryJson = objectMapper.writeValueAsString(inventory);

        when(inventoryService.update(any(InventoryEntity.class))).thenReturn(inventory);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(2L))
                .andExpect(jsonPath("$.saleCode").value(1L))
                .andExpect(jsonPath("$.productCode").value(1L))
                .andExpect(jsonPath("$.unitValue").value(10.25))
                .andExpect(jsonPath("$.detail").value("Compra"));
    }
}
