package SBAB.service;

import SBAB.external.classificationservice.ClassificationApi;
import SBAB.external.transactionservice.TransactionApi;
import SBAB.model.ClassifiedTransaction;
import SBAB.model.Interval;
import SBAB.model.Transaction;
import SBAB.repository.ClassifiedTransactionRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ClassifiedTransactionService {
    private ClassifiedTransactionRepository classifiedTransactionRepository;
    private TransactionApi transactionApi;
    private ClassificationApi classificationApi;

    @Autowired
    public ClassifiedTransactionService(ClassifiedTransactionRepository classifiedTransactionRepository, TransactionApi transactionApi, ClassificationApi classificationApi) {
        this.classifiedTransactionRepository = classifiedTransactionRepository;
        this.transactionApi = transactionApi;
        this.classificationApi = classificationApi;
    }

    public Flowable<ClassifiedTransaction> getClassifiedTransactionsByUserId(String userId) {
        return classifiedTransactionRepository.getTransactionsForUser(userId)
                .switchIfEmpty(classifyTransactionsForUser(userId));
    }

    public Flowable<ClassifiedTransaction> classifyTransactionsForUser(String userId) {
        return storeClassifiedTransactions(userId, transactionApi.getAllTransactions()
                .flatMapSingle(this::classifyTransaction)
        );
    }

    public Single<List<ClassifiedTransaction>> overrideClassifications(String userId, ClassifiedTransaction transaction){
        return classifiedTransactionRepository.putTransaction(userId, transaction);
    }

    public Flowable<ClassifiedTransaction> getTransactionsForUserInInterval(String userId, Interval interval){
        return classifiedTransactionRepository.getTransactionsForUser(userId)
                .switchIfEmpty(classifyTransactionsForUser(userId))
                .filter(t -> t.date().before(interval.end()))
                .filter(t -> t.date().after(interval.start()));
    }


    private Flowable<ClassifiedTransaction> storeClassifiedTransactions(String userId, Flowable<ClassifiedTransaction> classifiedTransactions) {
        return classifiedTransactions
                .toList()
                .flatMapPublisher(transactions -> classifiedTransactionRepository.putTransactionsForUser(userId, transactions));
    }

    private Single<ClassifiedTransaction> classifyTransaction(Transaction transaction) {
        return classificationApi.getClassificationForId(transaction.recipientId())
                .map(m -> classifyTransaction(transaction, m));

    }

    private ClassifiedTransaction classifyTransaction(Transaction transaction, Map<Integer, String> classification) {
        return new ClassifiedTransaction(transaction.date(),
                transaction.recipientId(),
                transaction.description(),
                transaction.amount(),
                classification.get(transaction.recipientId()),
                UUID.randomUUID().toString()
                );
    }

}
