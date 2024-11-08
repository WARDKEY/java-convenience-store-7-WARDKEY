package store.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Discount {
    private final String name;
    private final String buy;
    private final String get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Discount(String name, String buy, String get, String startDate, String endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
    }

    public String getName() {
        return name;
    }

    public String getBuy() {
        return buy;
    }

    public String getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
