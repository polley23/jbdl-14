package com.gfg.jbdl14majorprojectpaymentapp.transaction.controller;

import com.gfg.jbdl14majorprojectpaymentapp.exception.NotFoundException;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.TransactionRequest;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.service.TransactionService;
import com.sun.tools.internal.ws.processor.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @PostMapping("/transaction")
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity create(@RequestBody TransactionRequest transactionRequest, Authentication authentication){
        UsernamePasswordAuthenticationToken
                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getAuthorities());
        if(!usernamePasswordAuthenticationToken.getName().equals(transactionRequest.getFromUser())){
            ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(transactionRequest));
    }

    @GetMapping("/transaction/{tx_id}")
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity get(@PathVariable("tx_id") String txId){
        try {
            return ResponseEntity.ok(transactionService.get(txId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
