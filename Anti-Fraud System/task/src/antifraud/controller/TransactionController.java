package antifraud.controller;

import antifraud.model.request.AmountRequest;
import antifraud.model.response.ResultResponse;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/antifraud/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping
    ResultResponse post(@Valid @RequestBody AmountRequest amountRequest) {
        return new ResultResponse(transactionService.processAmount(amountRequest.getAmount()));
    }
}
