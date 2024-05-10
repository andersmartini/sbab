package SBAB.service;

import SBAB.external.classificationservice.ClassificationApi;
import SBAB.external.transactionservice.TransactionApi;
import SBAB.model.ClassifiedTransaction;
import SBAB.model.Transaction;
import SBAB.repository.ClassifiedTransactionRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return transactionApi.getAllTransactions()
                .flatMap(classificationApi::classifyRecipientId)
                .

    }

    private Single<ClassifiedTransaction> classifyTransaction(Transaction transaction){
        return classificationApi.classifyRecipientId(transaction.recipientId().)

    }

}
