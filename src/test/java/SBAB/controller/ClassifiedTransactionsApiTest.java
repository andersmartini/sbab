package SBAB.controller;

import SBAB.model.ClassifiedTransaction;
import io.reactivex.rxjava3.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassifiedTransactionsApiTest {

    @Autowired
    private ClassifiedTransactionsApi classifiedTransactionsApi;


    @Test
    void canGetClassifiedTransactions() {
        String userId = UUID.randomUUID().toString();
        List<ClassifiedTransaction> transactions = classifiedTransactionsApi.getTransactions(userId).blockingGet();
        assertEquals(66, transactions.size());
        assertEquals("ENTERTAINMENT", transactions.get(0).classification());
    }

    @Test
    void canOverrideClassification(){
        String userId = UUID.randomUUID().toString();
        String newClassification = "Hire Anders Martini";
        int randomTransactionIndex = 4; //selected by fair dice roll, guaranteed to be random ;) https://xkcd.com/221/
        List<ClassifiedTransaction> initialTransactions = classifiedTransactionsApi.getTransactions(userId).blockingGet();
        ClassifiedTransaction aTransaction = initialTransactions.get(randomTransactionIndex);
        ClassifiedTransaction updatedTransaction  = new ClassifiedTransaction(aTransaction.date(), aTransaction.recipientId(), aTransaction.description(), aTransaction.amount(), newClassification, aTransaction.id());
        List<ClassifiedTransaction> newTransactions = classifiedTransactionsApi.updateTransaction(userId, updatedTransaction).blockingGet();

        assertNotEquals(newTransactions.get(randomTransactionIndex), initialTransactions.get(randomTransactionIndex));
        assertEquals(updatedTransaction, newTransactions.get(randomTransactionIndex));
    }

}