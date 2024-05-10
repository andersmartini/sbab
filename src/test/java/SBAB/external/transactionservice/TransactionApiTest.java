package SBAB.external.transactionservice;

import SBAB.model.Transaction;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionApiTest {

    private static TransactionApi transactionRepository;

    @BeforeAll
    static void init()throws IOException{
        transactionRepository = new TransactionApi();
    }

    @Test
    void canGetTransactionsFromDate() {
        Flowable<Transaction> transactions = transactionRepository.getOutGoingTransactionsFromDate(new Date(1684082549386L));
        List<Transaction> transactionList = transactions.toList().blockingGet();
        assertEquals(39, transactionList.size());
    }

    @Test
    void canGetTransactionsBetweenDates(){
        Flowable<Transaction> transactions = transactionRepository.getOutgoingTransactionsBetweenDates(new Date(1684082549386L), new Date(1687082549386L));
        List<Transaction> transactionList = transactions.toList().blockingGet();
        assertEquals(7, transactionList.size());
    }

}