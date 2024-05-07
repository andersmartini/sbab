package SBAB.external.TransactionService;


import SBAB.model.Transaction;
import rx.Observable;

import java.util.Date;

// this would normally be a separate microservice, but for simplicity it's just a class here now
public class TransactionApi {

    public Observable<Transaction> getTransactions(Date date){
        return null;
    }



}
