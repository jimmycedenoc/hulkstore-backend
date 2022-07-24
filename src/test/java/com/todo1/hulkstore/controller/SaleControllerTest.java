package com.todo1.hulkstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo1.hulkstore.entity.SaleEntity;
import com.todo1.hulkstore.service.ISaleService;
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
@WebMvcTest(SaleController.class)
public class SaleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ISaleService saleService;

    @Test
    void getAllProducts() throws Exception {
        List<SaleEntity> saleEntityList = new ArrayList<SaleEntity>();
        saleEntityList.add(new SaleEntity(new Date(), 2L, 1L, 1L, 10.25));
        saleEntityList.add(new SaleEntity(new Date(), 2L, 1L, 1L, 10.00));
        when(saleService.getAllSales()).thenReturn(saleEntityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/sale")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void saveSaleSuccess() throws Exception {
        SaleEntity sale = new SaleEntity(new Date(), 2L, 1L, 1L, 10.25);
        ObjectMapper objectMapper = new ObjectMapper();
        String saleJson = objectMapper.writeValueAsString(sale);

        when(saleService.save(any(SaleEntity.class))).thenReturn(sale);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(saleJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(2L))
                .andExpect(jsonPath("$.userCode").value(1L))
                .andExpect(jsonPath("$.productCode").value(1L))
                .andExpect(jsonPath("$.unitValue").value(10.25));
    }

    @Test
    void updateSaleSuccess() throws Exception {
        SaleEntity sale = new SaleEntity(new Date(), 2L, 1L, 1L, 10.25);
        ObjectMapper objectMapper = new ObjectMapper();
        String saleJson = objectMapper.writeValueAsString(sale);

        when(saleService.update(any(SaleEntity.class))).thenReturn(sale);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(saleJson)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(2L))
                .andExpect(jsonPath("$.userCode").value(1L))
                .andExpect(jsonPath("$.productCode").value(1L))
                .andExpect(jsonPath("$.unitValue").value(10.25));
    }
}
