package IOManager.FileManager;

import IOManager.InputOrder.ConvertObject;
import models.InputObject;
import IOManager.JSON.JSON;
import models.JSONObject;
import java.io.*;

public class CommandFile extends FileManager {
    String fileName;
    String fileDirectory;
    JSON json;
    ConvertObject convertObject;
    File mainFile;
    String tableName;
    //todo save colItem

    public CommandFile(String tableName){
        this.tableName = tableName;
        fileName = "\\CommandFile.dat";
        setFolderDirectory(tableName);
        setFileDirectory();
        checkFileExist();
        json = new JSON();
    }

    public void setFileDirectory() {
        this.fileDirectory = super.folderDirectory + fileName;
    }

    public void checkFileExist (){
        setFolderDirectory(tableName);
        setFileDirectory();
        try {
            mainFile = new File (fileDirectory);
            RandomAccessFile file = new RandomAccessFile(mainFile, "rw");
            file.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void writeToFile(String input) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileDirectory, "rw");
            randomAccessFile.seek(0);
            randomAccessFile.writeBytes(input);
            randomAccessFile.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }


    public InputObject readFromFile(String tableName) {
        try {
            setFolderDirectory(tableName);
            setFileDirectory();

            RandomAccessFile file = new RandomAccessFile(fileDirectory, "r");
            byte[] bytes = new byte[(int)file.length()];
            file.read(bytes);
            file.close();

            String outFromFile = new String(bytes);

            return convertFileToInputObject(outFromFile);

        }catch(IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }


    private InputObject convertFileToInputObject (String fileInformation){
        JSONObject jsonObject = json.parserJSON(fileInformation);
        convertObject = new ConvertObject(jsonObject);
        return convertObject.convert();
    }

    public void deleteCommandFile (String tableName){
        setFolderDirectory(tableName);
        setFileDirectory();
        mainFile.deleteOnExit();
    }
}
