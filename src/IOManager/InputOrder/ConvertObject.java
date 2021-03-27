package IOManager.InputOrder;

import IOManager.FileManager.CommandFile;
import models.ColItem;
import models.InputObject;
import models.JSONField;
import models.JSONObject;

import java.util.ArrayList;

public class ConvertObject {
    private InputObject inputObject = new InputObject();
    private JSONObject jsonObject;
    private ColItem colItem;
    private String tableName;

    public ConvertObject(JSONObject jsonObject){
        tableName = (jsonObject.getItems().get(1).getValue()).toString();
        this.jsonObject = jsonObject;
    }

    public InputObject convert (){
        InputObject inputObject;
        switch ((jsonObject.getItems().get(0).getValue()).toString()){
            case "build" :
                return convertBuildAction();
            case "open" :
                return convertOpenAction();
            default :
                return null;
        }
    }


    public InputObject convertBuildAction (){
        String prime = (jsonObject.getItems().get(2).getValue().toString());

        for (int counter = 2 ; counter < jsonObject.getItems().size() ; counter++){
            colItem = new ColItem();
            if (jsonObject.getItems().get(counter).getKey().equals("addColumn")){
                colItem.setTableName(tableName);
                colItem.setPrime(prime);
                colItem.setFunction("addColumn");
                for (int counter2 = 0 ; counter2 < jsonObject.getItems().get(counter).getValueObject().getItems().size() ; counter2++) {
                    switch (jsonObject.getItems().get(counter).getValueObject().getItems().get(counter2).getKey()){
                        case "column name" :
                            colItem.setColName((jsonObject.getItems().get(counter).getValueObject().getItems().get(counter2).getValue()).toString());
                            if (((jsonObject.getItems().get(counter).getValueObject().getItems().get(counter2).getValue()).toString()).equals(prime)){
                                colItem.setPrimary(true);
                            }
                            break;
                        case "column type" :
                            colItem.setItemType((jsonObject.getItems().get(counter).getValueObject().getItems().get(counter2).getValue()).toString());
                            break;
                        case "character size" :
                            colItem.setColCharSize((Integer) jsonObject.getItems().get(counter).getValueObject().getItems().get(counter2).getValue());
                            break;
                    }
                }
                inputObject.addColItem(colItem);
            }
        }
        return inputObject;
    }


    public InputObject convertOpenAction (){
        CommandFile commandFile = new CommandFile(tableName);
        InputObject inputObject = commandFile.readFromFile(tableName);

        switch (jsonObject.getItems().get(2).getKey()){
            case "addRow":
                return convertJsonToInputObject(inputObject, jsonObject, "addRow");
            case "prime" :
                switch (jsonObject.getItems().get(3).getKey()){
                    case "edit" :
                        return convertJsonToInputObject(inputObject, jsonObject, "edit");
                    case "search" :
                        if (jsonObject.getItems().get(3).getValue() instanceof String){
                            return convertSearchRowFunction((jsonObject.getItems().get(2).getValue()).toString());
                        } else {
                            return convertJsonToInputObject(inputObject, jsonObject, "search");
                        }
                    case "delete" :
                        if (jsonObject.getItems().get(3).getValue() instanceof String){
                            return convertDeleteRowFunction((jsonObject.getItems().get(2).getValue()).toString());
                        } else {
                            return convertJsonToInputObject(inputObject, jsonObject, "delete");
                        }
                }
            case "delete" :
                return convertDeleteTableFunction();
            default:
                return null;
        }
    }


    private InputObject convertJsonToInputObject(InputObject inputObject, JSONObject jsonObject, String function){
        InputObject objects = new InputObject();
        ArrayList<JSONField<?>> items = getItems(jsonObject, function);
        for (int counter = 0 ; counter < items.size() ; counter++){
            if (items.get(counter).getKey().equals("column name")) {
                colItem = new ColItem();
                colItem.setTableName(tableName);
                colItem.setColName((items.get(counter).getValue()).toString());
                colItem.setFunction(function);
                if (function.equals("addRow") || function.equals("edit")) {
                    colItem.setItemValue(items.get(counter + 1).getValue());
                }
                for (ColItem colItem1 : inputObject.getColItems()) {
                    if ((items.get(counter).getValue()).equals(colItem1.getColName())) {
                        if (function.equals("addRow")) {
                            colItem.setPrime(colItem1.getPrime());
                        } else if (function.equals("edit") || function.equals("search") || function.equals("delete")) {
                            colItem.setPrime((jsonObject.getItems().get(2).getValue()).toString());
                        }
                        colItem.setItemType(colItem1.getItemType());
                        colItem.setColCharSize(colItem1.getColCharSize());
                        colItem.setPrimary(colItem1.isPrimary());
                    }
                }
                objects.addColItem(colItem);
            }
        }
        return objects;
    }

    private ArrayList<JSONField<?>> getItems (JSONObject jsonObject, String function){
        ArrayList<JSONField<?>> items = new ArrayList<>();
        if (jsonObject.getItems().get(2).getKey().equals("addRow")){
            return jsonObject.getItems().get(2).getValueObject().getItems();

        } else if (jsonObject.getItems().get(3).getKey().equals("search")){
            return jsonObject.getItems().get(3).getValueObject().getItems();

        } else if (jsonObject.getItems().get(3).getKey().equals("edit")){
            return jsonObject.getItems().get(3).getValueObject().getItems();

        } else if (jsonObject.getItems().get(3).getKey().equals("delete")) {
            return jsonObject.getItems().get(3).getValueObject().getItems();

        }
        return items;
    }


    private InputObject convertSearchRowFunction(String prime){
        colItem = new ColItem();
        colItem.setPrime(prime);
        colItem.setTableName(tableName);
        colItem.setFunction("search row");
        inputObject.addColItem(colItem);
        return inputObject;
    }

    private InputObject convertDeleteRowFunction(String prime){
        colItem = new ColItem();
        colItem.setPrime(prime);
        colItem.setTableName(tableName);
        colItem.setFunction("delete row");
        inputObject.addColItem(colItem);
        return inputObject;
    }

    private InputObject convertDeleteTableFunction(){
        colItem = new ColItem();
        colItem.setTableName(tableName);
        colItem.setFunction("delete table");
        inputObject.addColItem(colItem);
        return inputObject;
    }
}


