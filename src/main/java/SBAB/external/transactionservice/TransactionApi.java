package SBAB.external.transactionservice;

import SBAB.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.reactivex.rxjava3.core.Flowable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionApi {
    private List<Transaction> transactions;

    public TransactionApi() throws IOException {
        this.init();
    }

    public Flowable<Transaction> getAllTransactions(){
        return Flowable.fromIterable(transactions);
    }

    public Flowable<Transaction> getAllOutgoinTransactions() {
        return Flowable.fromIterable(transactions)
                .filter(t -> t.amount() < 0);
    }

    public Flowable<Transaction> getOutGoingTransactionsFromDate(Date date) {
        return getAllOutgoinTransactions()
                .filter(transaction -> transaction.date().after(date));
    }

    public Flowable<Transaction> getOutgoingTransactionsBetweenDates(Date fromDate, Date toDate) {
        return getOutGoingTransactionsFromDate(fromDate)
                .filter(transaction -> transaction.date().before(toDate));
    }



    private void init() throws IOException {
        Resource jsonData = new ClassPathResource("transactions.json");
        Type transactionListType = new TypeToken<ArrayList<Transaction>>() {}.getType();
        transactions = new Gson().fromJson(jsonData.getContentAsString(StandardCharsets.UTF_8), transactionListType);
    }



}
