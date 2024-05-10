package SBAB.controller;

import SBAB.model.ClassifiedTransaction;
import SBAB.service.ClassifiedTransactionService;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassifiedTransactionsApi {
    private ClassifiedTransactionService classifiedTransactionService;

    @Autowired
    public ClassifiedTransactionsApi(ClassifiedTransactionService classifiedTransactionService){
    this.classifiedTransactionService = classifiedTransactionService;
    }

    @GetMapping("/transactions/{userId}")
    public Single<List<ClassifiedTransaction>> getTransactions(String userId) {
        classifiedTransactionService.getClassifiedTransactionsByUserId(userId);
    }

    @PutMapping("/transactions")
    public Single<List<ClassifiedTransaction>> updateTransactions(@RequestBody List<ClassifiedTransaction> classifiedTransactions) {}>



}
