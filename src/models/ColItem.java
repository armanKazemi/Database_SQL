package models;

import java.io.Serializable;

public class ColItem implements Serializable {

    String tableName ;
    String colName ;
    String function;
    String prime = null;
    Object itemValue = null;
    String itemType = null;
    int colCharSize = 0;
    boolean isPrimary = false ;

    public ColItem() {
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public void setPrime(String prime) {
        this.prime = prime;
    }

    public String getPrime() {
        return prime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getColCharSize() {
        return colCharSize;
    }

    public void setColCharSize(int colCharSize) {
        this.colCharSize = colCharSize;
    }

    public Object getItemValue() {
        return itemValue;
    }

    public void setItemValue(Object itemValue) {
        this.itemValue = itemValue;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary ;
    }

    public boolean isPrimary(){
        return isPrimary;
    }

}
