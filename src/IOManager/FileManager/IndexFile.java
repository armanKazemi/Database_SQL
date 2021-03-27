package IOManager.FileManager;

import models.IndexList;
import java.io.*;

public class IndexFile extends FileManager {
    String fileName;
    String fileDirectory;
    File file;
    String tableName;

    public IndexFile(String tableName){
        this.tableName = tableName;
        fileName = "\\indexFile.dat";
        setFolderDirectory(tableName);
        setFileDirectory();
        checkFileExist();
    }

    public void setFileDirectory() {
        this.fileDirectory = super.folderDirectory + fileName;
    }


    public void checkFileExist (){
        try {
            file = new File(fileDirectory);
            if (file.length() <= 8) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
                outputStream.writeObject(new IndexList());
                outputStream.close();
                fileOutputStream.close();
            }
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void writeObject (IndexList input) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileDirectory));
            outputStream.writeObject(input);
            outputStream.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public IndexList readObject (String tableName) {
        IndexList indexList = new IndexList();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileDirectory));
            indexList = (IndexList) inputStream.readObject();
            inputStream.close();
            return indexList;
        } catch(IOException | ClassNotFoundException exception){
            exception.printStackTrace();
            return null;
        }
    }

    public void deleteIndexFile (String tableName){
        setFolderDirectory(tableName);
        setFileDirectory();
        file.deleteOnExit();
    }
}
