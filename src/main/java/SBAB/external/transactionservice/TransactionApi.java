package SBAB.external.transactionservice;

import SBAB.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import rx.Observable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionApi {
    private List<Transaction> transactions;

    public TransactionApi() throws IOException {
        this.init();
    }

    public Observable<Transaction> getAllOutgoinTransactions() {
        return Observable.from(transactions)
                .filter(t -> t.amount() < 0);
    }

    public Observable<Transaction> getOutGoingTransactionsFromDate(Date date) {
        return getAllOutgoinTransactions()
                .filter(transaction -> transaction.date().after(date));
    }

    public Observable<Transaction> getOutgoingTransactionsBetweenDates(Date fromDate, Date toDate) {
        return getOutGoingTransactionsFromDate(fromDate)
                .filter(transaction -> transaction.date().before(toDate));
    }



    private void init() throws IOException {
        Resource jsonData = new ClassPathResource("transactions.json");
        Type transactionListType = new TypeToken<ArrayList<Transaction>>() {}.getType();
        transactions = new Gson().fromJson(jsonData.getContentAsString(StandardCharsets.UTF_8), transactionListType);

    }



}
