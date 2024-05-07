package SBAB.external.transactionservice;

import SBAB.model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import rx.Observable;

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
        Observable<Transaction> transactions = transactionRepository.getOutGoingTransactionsFromDate(new Date(1684082549386L));
        List<Transaction> tranList = transactions.toList().toBlocking().first();
        assertEquals(39, tranList.size());
    }

    @Test
    void canGetTransactionsBetweenDates(){
        Observable<Transaction> transactions = transactionRepository.getOutgoingTransactionsBetweenDates(new Date(1684082549386L), new Date(1687082549386L));
        List<Transaction> tranList = transactions.toList().toBlocking().first();
        assertEquals(7, tranList.size());
    }

}