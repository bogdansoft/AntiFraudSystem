package antifraud.service;

import antifraud.model.Result;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public Result processAmount(Long amount) {
        Result res;
        if (amount <= 200) {
            res = Result.ALLOWED;
        } else if (amount <= 1500) {
            res = Result.MANUAL_PROCESSING;
        } else {
            res = Result.PROHIBITED;
        }
        return res;
    }
}
