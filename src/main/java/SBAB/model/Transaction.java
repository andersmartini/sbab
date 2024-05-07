package SBAB.model;

import java.util.Date;

public record Transaction(Date date, Integer recipientId, String description, int amount) {}
