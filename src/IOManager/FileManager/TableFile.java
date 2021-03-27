package IOManager.FileManager;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class TableFile extends FileManager{
    String fileName;
    String fileDirectory;
    long fileSize = 0;
    File mainFile;
    String tableName;

    public TableFile(String tableName){
        this.tableName = tableName;
        fileName = "\\tableFile.dat";
        setFolderDirectory(tableName);
        setFileDirectory();
        checkFileExist();
    }

    public void setFileDirectory() {
        this.fileDirectory = super.folderDirectory + fileName;
    }

    public void checkFileExist (){
        setFolderDirectory(tableName);
        setFileDirectory();
        try {
            mainFile = new File(fileDirectory);
            RandomAccessFile file = new RandomAccessFile(mainFile, "rw");
            file.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }


    public void writeString (String input, long position, int size) {
        char[] chars = new char[size];
        for (int counter = 0 ; counter < size ; counter++){
            if (counter < input.length()){
                chars[counter] = input.charAt(counter);
            } else {
                chars[counter] = ' ';
            }
        }
        input = String.valueOf(chars);
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "rw");
            file.seek(position);
            file.write(input.getBytes());
            file.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void writeInt (int input, long position) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "rw");
            file.seek(position);
            file.writeInt(input);
            file.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void writeDouble (double input, long position) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "rw");
            file.seek(position);
            file.writeDouble(input);
            file.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void writeChar (char input, long position) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "rw");
            file.seek(position);
            file.writeChar(input);
            file.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public String readString (long position, int size) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "r");
            file.seek(position);
            byte[] bytes = new byte[size];
            file.read(bytes, (int)position ,size);
            file.close();
            return new String(bytes);
        } catch(IOException exception){
            exception.printStackTrace();
            return null;
        }
    }

    public int readInt (long position) {
        int integer;
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "r");
            file.seek(position);
            integer = file.readInt();
            file.close();
            return integer;
        } catch(IOException exception){
            exception.printStackTrace();
            return 0;
        }
    }

    public double readDouble (long position) {
        double doubles;
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "r");
            file.seek(position);
            doubles = file.readDouble();
            file.close();
            return doubles;
        } catch(IOException exception){
            exception.printStackTrace();
            return 0;
        }
    }

    public char readChar (long position) {
        char chars;
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "r");
            file.seek(position);
            chars = file.readChar();
            file.close();
            return chars;
        } catch(IOException exception){
            exception.printStackTrace();
            return 0;
        }
    }

    public long getFileSize(){
        try {
            RandomAccessFile file = new RandomAccessFile(fileDirectory, "rw");
            fileSize =  file.length();
            file.close();
            return fileSize;
        } catch(IOException exception){
            exception.printStackTrace();
            return 0;
        }
    }

    public void deleteTableFile (String tableName){
        setFolderDirectory(tableName);
        setFileDirectory();
        mainFile.deleteOnExit();
    }
}
