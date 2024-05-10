package SBAB.repository;

import SBAB.model.ClassifiedTransaction;
import io.reactivex.rxjava3.core.Flowable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassifiedTransactionRepository {
    private Map<String, List<ClassifiedTransaction>> storedTransactions;

    public ClassifiedTransactionRepository() {
        this.storedTransactions = new HashMap<>();
    }

    public Flowable<ClassifiedTransaction> getTransactionsForUser(String userId) {
        return Flowable.fromIterable(storedTransactions.get(userId));
    }

    public Flowable<ClassifiedTransaction> putTransactionsForUser(String userId, List<ClassifiedTransaction> classifiedTransactions) {
        storedTransactions.put(userId, classifiedTransactions);
        return Flowable.fromIterable(classifiedTransactions);
    }

}
