package SBAB.external.classificationservice;

import SBAB.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassificationApi {
    private Map<String, String> classifications;

    public ClassificationApi() throws IOException {
        init();
    }

    public Single<Map<String, String>> classifyRecipientId(String recipientId) {
        return Single.just(Collections.singletonMap(recipientId, classifications.getOrDefault(recipientId, "UNKNOWN")));
    }

    public Single<Map<String, String>> classifyRecipients(List<String> recipientIds) {
        return Flowable.fromIterable(recipientIds)
                .toMap(id->id, id-> classifications.get(id));
    }

    private void init() throws IOException {
        Resource jsonData = new ClassPathResource("classifications.json");
        Type transactionListType = new TypeToken<HashMap<String, String>>() {}.getType();
        classifications = new Gson().fromJson(jsonData.getContentAsString(StandardCharsets.UTF_8), transactionListType);

    }
}
