package SBAB.controller;

import SBAB.model.ClassifiedTransaction;
import SBAB.model.Interval;
import SBAB.service.ClassifiedTransactionService;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassifiedTransactionsApi {
    private ClassifiedTransactionService classifiedTransactionService;

    @Autowired
    public ClassifiedTransactionsApi(ClassifiedTransactionService classifiedTransactionService){
    this.classifiedTransactionService = classifiedTransactionService;
    }

    @GetMapping("/transactions/{userId}")
    public Single<List<ClassifiedTransaction>> getTransactions(@PathVariable String userId) {
        return classifiedTransactionService.getClassifiedTransactionsByUserId(userId).toList();
    }

    @PostMapping("/transactions/{userId}/bydate")
    public Single<List<ClassifiedTransaction>> getTransactionsByDate(@PathVariable String userId, @RequestBody Interval interval){
        return classifiedTransactionService.getTransactionsForUserInInterval(userId, interval).toList();
    }

    @PutMapping("/transactions/{userId}")
    public Single<List<ClassifiedTransaction>> updateTransaction(@PathVariable String userId, @RequestBody ClassifiedTransaction classifiedTransaction) {
        return classifiedTransactionService.overrideClassifications(userId, classifiedTransaction);
    }


}
