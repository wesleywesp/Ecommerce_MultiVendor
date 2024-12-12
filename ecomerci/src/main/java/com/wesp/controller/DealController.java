package com.wesp.controller;

import com.wesp.model.Deal;
import com.wesp.response.ApiResponse;
import com.wesp.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
public class DealController {
    private final DealService dealService;

    @PostMapping
    public ResponseEntity<Deal> creatDeals(Deal deal){
        return ResponseEntity.ok(dealService.createDeal(deal));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id,
                                           @RequestBody Deal deal){
        Deal updateDeal = dealService.updateDeal(deal);
        return ResponseEntity.ok(updateDeal);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id){
        dealService.deleteDeal(id);
        ApiResponse apiResponse = new ApiResponse("Deal deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
