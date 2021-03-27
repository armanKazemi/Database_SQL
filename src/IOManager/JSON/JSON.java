package IOManager.JSON;

import IOManager.FileManager.CommandFile;
import models.ColItem;
import models.InputObject;
import models.JSONField;
import models.JSONObject;
import parser.JSONParser;

import java.util.ArrayList;

public class JSON {
    private JSONObject jsonObject;
    private String inputOrder;

    public JSON() {
    }


    public JSONObject parserJSON (String inputOrder){
        boolean isValid;
        this.inputOrder = inputOrder;

        jsonObject = new JSONParser().parse(this.inputOrder);

        isValid = checkValidObject(jsonObject, inputOrder);
        return (isValid) ? jsonObject : null;
    }


    private boolean checkValidObject (JSONObject jsonObject, String inputOrder){
        String tableName = (String) jsonObject.getItems().get(1).getValue();
        CommandFile commandFile = new CommandFile(tableName);

        if (jsonObject.getItems().get(0).getValue().equals("build")){

            commandFile.makeFolder(tableName);
            commandFile.writeToFile(inputOrder);
            return true;

        } else if (jsonObject.getItems().get(0).getValue().equals("open")) {
            InputObject inputObject = commandFile.readFromFile(tableName);
            return compare (inputObject, jsonObject);

        } else {
            return false;
        }
    }


    private boolean compare (InputObject inputObject, JSONObject jsonObject){
        switch (jsonObject.getItems().get(2).getKey()){
            case "addRow":
                return checkValidation (inputObject, jsonObject);

            case "prime" :
                for (ColItem colItem : inputObject.getColItems()){
                    if (colItem.isPrimary()){
                        if (!checkField(jsonObject.getItems().get(2), colItem)){
                            return false;
                        }
                    }
                }
                switch (jsonObject.getItems().get(3).getKey()){
                    case "edit" :
                        return checkValidation(inputObject, jsonObject);

                    case "search" :
                    case "delete" :
                        if (jsonObject.getItems().get(3).getValue() instanceof String){
                            return jsonObject.getItems().get(3).getValue().equals("row");
                        }
                        return checkValidColumnName(inputObject, jsonObject);

                    default :
                        return false;
                }

            case "delete" :
                return jsonObject.getItems().get(2).getValue().equals("table");

            default :
                return false;
        }
    }


    private boolean checkValidation (InputObject inputObject, JSONObject jsonObject){
        boolean checkValidColumnName, checkValidColumnField;
        checkValidColumnName = checkValidColumnName(inputObject, jsonObject);
        checkValidColumnField = checkValidColumnField(inputObject, jsonObject);
        return checkValidColumnField && checkValidColumnName;
    }


    private boolean checkValidColumnName (InputObject inputObject, JSONObject jsonObject){

        for (JSONField<?> field : jsonObject.getItems()){
            if (field.getKey().equals("addRow") || field.getKey().equals("edit") || field.getKey().equals("search") || field.getKey().equals("delete")){
                for (JSONField<?> field1 :field.getValueObject().getItems()){
                    if (field1.getKey().equals("column name")){
                        for (int inputObjectCounter = 0 ; inputObjectCounter < inputObject.getColItems().size() ; inputObjectCounter++){
                            ColItem colItem = inputObject.getColItems().get(inputObjectCounter);
                            if (colItem.getColName().equals(field1.getValue())) {
                                break;
                            } else if (inputObjectCounter == inputObject.getColItems().size() && !colItem.getColName().equals(field1.getValue())) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    private boolean checkValidColumnField (InputObject inputObject, JSONObject jsonObject){
        ArrayList<JSONField<?>> items;

        for (JSONField<?> field : jsonObject.getItems()){
            if (field.getKey().equals("addRow") || field.getKey().equals("edit")){
                items = field.getValueObject().getItems();
                for (int counter = 0 ; counter < items.size() ; counter += 2){
                    if (items.get(counter).getKey().equals("column name") && items.get(counter+1).getKey().equals("field")){

                        for (int inputObjectCounter = 0 ; inputObjectCounter < inputObject.getColItems().size() ; inputObjectCounter++) {
                            ColItem colItem = inputObject.getColItems().get(inputObjectCounter);
                            if (colItem.getColName().equals(items.get(counter).getValue())) {
                                if (checkField(items.get(counter + 1), colItem)) {
                                    break;
                                } else if (inputObjectCounter == inputObject.getColItems().size() && !checkField(items.get(counter + 1), colItem)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean checkField (JSONField<?> jsonField, ColItem colItem){
        if (jsonField.getValue() instanceof Integer){
            return (colItem.getItemType().equals("int") || colItem.getItemType().equals("integer"));
        } else if (jsonField.getValue() instanceof Double) {
            return colItem.getItemType().equals("double");
        } else if (jsonField.getValue() instanceof Character){
            return colItem.getItemType().equals("char");
        } else if (jsonField.getValue() instanceof String){
            return  (colItem.getItemType().equals("string") && colItem.getColCharSize() >= ((String)jsonField.getValue()).length());
        }
        return false;
    }
}
