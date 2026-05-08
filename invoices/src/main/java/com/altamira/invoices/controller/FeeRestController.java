package com.altamira.invoices.controller;


import com.altamira.invoices.dto.FeeBatchDTO;
import com.altamira.invoices.dto.FeeDTO;
import com.altamira.invoices.service.FeeService;
import com.altamira.common.validator.groups.OnPatchBatchUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/fees")
@RestController
@RequiredArgsConstructor
public class FeeRestController {

    private final FeeService feeService;

    @GetMapping("list-all")
    public ResponseEntity<List<FeeDTO>> getAllFees(){
        return ResponseEntity.ok(feeService.getAllFees());
    }


    @PatchMapping("update-list")
    public ResponseEntity<Void> updateFees(@Validated(OnPatchBatchUpdate.class) @RequestBody FeeBatchDTO batchDto){
        feeService.updateFees(batchDto.feesDto());
        return ResponseEntity.ok().build();
    }

}
