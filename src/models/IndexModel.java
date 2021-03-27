package models;

import java.io.Serializable;

public class IndexModel<T> implements Serializable {
    private String primeColumn = null;
    private long tableAddress = 0;

    public void setPrimeColumn(String primeColumn) {
        this.primeColumn = primeColumn;
    }

    public void setTableAddress(long tableAddress) {
        this.tableAddress = tableAddress;
    }

    public long getTableAddress() {
        return tableAddress;
    }

    public String getPrimeColumn() {
        return primeColumn;
    }
}