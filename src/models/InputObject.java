package models;

import java.util.ArrayList;

public class InputObject {
    private ArrayList<ColItem> colItems = new ArrayList<>();

    public void setColItems(ArrayList<ColItem> colItems) {
        this.colItems = colItems;
    }

    public ArrayList<ColItem> getColItems() {
        return colItems;
    }

    public void addColItem (ColItem colItem){
        colItems.add(colItem);
    }

}


