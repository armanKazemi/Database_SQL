package IOManager.FileManager;

import java.io.File;

public class FileManager {

    String folderName;
    String folderDirectory;

    public String getFolderName() {
        return folderName;
    }

    public String getFolderDirectory() {
        return folderDirectory;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFolderDirectory(String folderName) {
        setFolderName (folderName);
        folderDirectory = System.getProperty("user.dir") + "\\" + folderName;
    }

    public void makeFolder(String tableName) {
        setFolderDirectory(tableName);
        File file = new File(folderDirectory);
        boolean bool = file.mkdirs();
    }

}
