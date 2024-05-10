package SBAB.model;

import java.util.Date;

public record ClassifiedTransaction(Date date, Integer recipientId, String description, int amount, String classification, String id) {


}

