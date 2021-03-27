package models;

import java.io.Serializable;
import java.util.ArrayList;

public class IndexList implements Serializable {
    private ArrayList<IndexModel> indexList = new ArrayList<>();

    public void setIndexList(ArrayList<IndexModel> indexList) {
        this.indexList = indexList;
    }

    public ArrayList<IndexModel> getIndexList() {
        return indexList;
    }

    public void addIndexModel (IndexModel indexModel){
        indexList.add(indexModel);
    }
}
