package SBAB.repository;

import SBAB.model.ClassifiedTransaction;
import SBAB.model.Interval;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return Flowable.fromIterable(storedTransactions.getOrDefault(userId, new ArrayList<>()));
    }

    public Maybe<ClassifiedTransaction> getTransactionById(String userID, String transactionID) {
        return getTransactionsForUser(userID)
                .filter(t -> t.id().equals(transactionID))
                .firstElement();
    }

    public Single<List<ClassifiedTransaction>> putTransaction(String userId, ClassifiedTransaction transaction) {
        return getTransactionsForUser(userId)
                .map(t -> updateIfMatch(t, transaction))
                .toList()
                .doOnSuccess(transactions -> storedTransactions.put(userId,transactions));
    }

    private ClassifiedTransaction updateIfMatch(ClassifiedTransaction old, ClassifiedTransaction newTransaction) {
        if(old.id().equals(newTransaction.id())){
            return newTransaction;
        }
        return old;
    }

    public Flowable<ClassifiedTransaction> putTransactionsForUser(String userId, List<ClassifiedTransaction> classifiedTransactions) {
        storedTransactions.put(userId, classifiedTransactions);
        return Flowable.fromIterable(classifiedTransactions);
    }

}
