package TableManager;

import IOManager.FileManager.CommandFile;
import IOManager.FileManager.IndexFile;
import IOManager.FileManager.TableFile;
import models.ColItem;
import models.InputObject;
import models.IndexList;
import models.IndexModel;

import java.io.PrintStream;


public class Table {
    private InputObject inputObject;
    private CommandFile commandFile;
    private IndexFile indexFile;
    private TableFile tableFile;
    private String tableName;
    private PrintStream printStream;
    private InputObject commandFileObject;

    public Table(InputObject inputObject){
        this.inputObject = inputObject;
        commandFile = new CommandFile(inputObject.getColItems().get(0).getTableName());
        indexFile = new IndexFile(inputObject.getColItems().get(0).getTableName());
        tableFile = new TableFile(inputObject.getColItems().get(0).getTableName());
        tableName = inputObject.getColItems().get(0).getTableName();
        printStream = System.out;
        commandFileObject = commandFile.readFromFile(tableName);
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void handle (){
        switch (inputObject.getColItems().get(0).getFunction()){
            case "addColumn" :
                break;
            case "addRow" :
                addRow(inputObject);
                break;
            default:
                chooseFunction(inputObject);
        }
    }

    private void chooseFunction (InputObject inputObject){
        for (ColItem colItem : inputObject.getColItems()) {
            switch (colItem.getFunction()) {
                case "search":
                    search(colItem);
                    break;
                case "search row" :
                    searchRow(colItem);
                    break;
                case "edit":
                    edit(colItem);
                    break;
                case "delete":
                    delete(colItem);
                    break;
                case "delete table":
                    deleteTable(colItem);
                    break;
                case "delete row":
                    deleteRow(colItem);
                    break;
                default:
            }
        }
    }


    private void addRow (InputObject inputObject){
        setIndexFile(inputObject);
        setTableFile(inputObject);
    }

    private void setIndexFile (InputObject inputObject){
        IndexList indexList = indexFile.readObject(tableName);
        IndexModel<?> indexModel = new IndexModel<>();

        for (ColItem colItem : inputObject.getColItems()){
            if (colItem.getColName().equals(commandFileObject.getColItems().get(0).getPrime())){
                indexModel.setPrimeColumn ((colItem.getItemValue()).toString());
                indexModel.setTableAddress(tableFile.getFileSize());
                break;
            }
        }
        indexList.addIndexModel(indexModel);
        indexFile.writeObject(indexList);
    }

    private void setTableFile (InputObject inputObject){
        for (ColItem colCommand : commandFileObject.getColItems()) {
            for (ColItem colItem : inputObject.getColItems()){
                if (colCommand.getColName().equals(colItem.getColName())){
                    if (colItem.getItemType().equals("string")){
                        tableFile.writeString((String) colItem.getItemValue(), tableFile.getFileSize(), colCommand.getColCharSize());
                    } else if (colItem.getItemType().equals("int") || colItem.getItemType().equals("integer")){
                        tableFile.writeInt((int) colItem.getItemValue(), tableFile.getFileSize());
                    } else if (colItem.getItemType().equals("double")){
                        tableFile.writeDouble((double) colItem.getItemValue(), tableFile.getFileSize());
                    } else if (colItem.getItemType().equals("char")){
                        tableFile.writeChar((char) colItem.getItemValue(), tableFile.getFileSize());
                    }
                }
            }
        }

    }

    private void search (ColItem colItem){
        long position = 0;
        IndexList indexList = indexFile.readObject(tableName);
        for (IndexModel<?> indexModel : indexList.getIndexList()){
            if (indexModel.getPrimeColumn().equals(colItem.getPrime())){
                position = indexModel.getTableAddress() + countFieldSizeByte(colItem.getColName());
                for (ColItem colCommand : commandFileObject.getColItems()){
                    if (colItem.getColName().equals(colCommand.getColName())){
                        if (colCommand.getItemType().equals("string")){
                            printStream.println(tableFile.readString(position,colCommand.getColCharSize()));
                        } else if (colCommand.getItemType().equals("int") || colCommand.getItemType().equals("integer")){
                            printStream.println(tableFile.readInt(position));
                        } else if (colCommand.getItemType().equals("double")){
                            printStream.println(tableFile.readDouble(position));
                        } else if (colCommand.getItemType().equals("char")){
                            printStream.println(tableFile.readChar(position));
                        }
                    }
                }
            }
        }
    }

    private void searchRow (ColItem colItem){
        ColItem colSearch = new ColItem();
        colSearch.setPrime(colItem.getPrime());
        for (ColItem colCommand : commandFileObject.getColItems()){
            colSearch.setColName(colCommand.getColName());
            search(colSearch);
        }
    }

    private void edit (ColItem colItem){
        long position = 0;
        IndexList indexList = indexFile.readObject(tableName);
        for (IndexModel<?> indexModel : indexList.getIndexList()){
            if (indexModel.getPrimeColumn().equals(colItem.getPrime())){
                position = indexModel.getTableAddress() + countFieldSizeByte(colItem.getColName());
                for (ColItem colCommand : commandFileObject.getColItems()){
                    if (colItem.getColName().equals(colCommand.getColName())){
                        if (colCommand.getItemType().equals("string")){
                            tableFile.writeString((colItem.getItemValue()).toString(), position, colCommand.getColCharSize());
                        } else if (colCommand.getItemType().equals("int") || colCommand.getItemType().equals("integer")){
                            tableFile.writeInt((int) colItem.getItemValue(), position);
                        } else if (colCommand.getItemType().equals("double")){
                            tableFile.writeDouble((double) colItem.getItemValue(), position);
                        } else if (colCommand.getItemType().equals("char")){
                            tableFile.writeChar((char) colItem.getItemValue(), position);
                        }
                    }
                }
            }
        }
    }

    private void delete (ColItem colItem) {
        for (ColItem colCommand : commandFileObject.getColItems()){
            if (colItem.getColName().equals(colCommand.getColName())){
                if (colCommand.getItemType().equals("string")){
                    colItem.setItemValue(" ");
                } else if (colCommand.getItemType().equals("int") || colCommand.getItemType().equals("integer")){
                    colItem.setItemValue(0);
                } else if (colCommand.getItemType().equals("double")){
                    colItem.setItemValue(0.0);
                } else if (colCommand.getItemType().equals("char")){
                    colItem.setItemValue(' ');
                }
            }
        }
        edit(colItem);
    }

    private void deleteRow (ColItem colItem) {
        IndexList indexList = indexFile.readObject(tableName);
        ColItem colDelete = new ColItem();
        colDelete.setPrime(colItem.getPrime());
        for (ColItem colCommand : commandFileObject.getColItems()) {
            if (colCommand.isPrimary()) {
                for (IndexModel<?> indexModel : indexList.getIndexList()) {
                    if (indexModel.getPrimeColumn().equals(colItem.getPrime())) {
                        indexModel.setTableAddress(0);
                        indexModel.setPrimeColumn(" ");
                        break;
                    }
                }
                colDelete.setColName(colCommand.getColName());
                delete(colDelete);
            }
        }
        indexFile.writeObject(indexList);
    }

    private void deleteTable (ColItem colItem){
        commandFile.deleteCommandFile(tableName);
        indexFile.deleteIndexFile(tableName);
        tableFile.deleteTableFile(tableName);
        System.exit(0);
    }


    private int countFieldSizeByte (String colName){
        int fieldPosition = 0;
        for (ColItem colItem : commandFileObject.getColItems()){
            if (colItem.getColName().equals(colName)){
                return fieldPosition;
            }
            if (colItem.getItemType().equals("int") || colItem.getItemType().equals("integer")){
                fieldPosition += 4;
            } else if (colItem.getItemType().equals("double")){
                fieldPosition += 8;
            } else if(colItem.getItemType().equals("char")){
                fieldPosition += 2;
            } else if (colItem.getItemType().equals("string")){
                fieldPosition += (colItem.getColCharSize());
            }
        }
        return fieldPosition;
    }
}
