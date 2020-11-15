package net.mckitsu.lib.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
 * 檔案管理器.
 *
 * @author  ZxyKira
 */
public class FileManager extends FolderManager {
    private final Path filePath;
    private final String fileName;

    /**
     * 建構子
     *
     * @param dirPath 資料夾路徑.
     * @param fileName 檔案名稱.
     */
    public FileManager(String dirPath,String fileName){
        super(dirPath);
        this.filePath = Paths.get(getDirPath(), fileName);
        this.fileName = fileName;
    }

    /**
     * 建構子
     *
     * @param dirManager 資料夾管器.
     * @param fileName 檔案名稱.
     */
    public FileManager(FolderManager dirManager, String fileName){
        this(dirManager.toString(), fileName);
    }

    /**
     * 建構子.
     *
     * @param dirPath 資料夾路徑URI.
     * @param fileName 檔案名稱.
     */
    public FileManager(URI dirPath, String fileName){
        super(dirPath);
        this.filePath = Paths.get(getDirPath(), fileName);
        this.fileName = fileName;
    }

    /**
     * 檢測檔案是否存在.
     *
     * @return true如果檔案存在; false檔案不存在.
     */
    public boolean exists(){
        return Files.exists(this.filePath);
    }

    /**
     * 建立新檔案.
     *
     * @return true檔案已被建立; false檔案已存在或建立失敗.
     */
    public boolean createFile(){
        this.createDir();

        try {
            Files.createFile(filePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 刪除檔案.
     *
     * @return true刪除檔案成功; false檔案不存在或刪除失敗.
     */
    public boolean deleteFile(){
        try {
            return Files.deleteIfExists(this.filePath);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 取得檔案名稱.
     *
     * @return 檔案名稱.
     */
    public String getFileName(){
        return fileName;
    }

    /**
     * 取得檔案path
     * @return 檔案含路徑path
     */
    @Override
    public String toString(){
        return this.filePath.toString();
    }

    /**
     * 將來源複製至此.
     *
     * @param source 來源目標，FileManager類別.
     * @return true複製檔案成功; false檔案不存在或複製失敗.
     */
    public boolean copy(FileManager source){
        try {
            Files.copy(source.filePath, this.filePath,  StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 將來源複製至此.
     *
     * @param source 來源目標，ByteArray類別.
     * @return true複製檔案成功; false檔案不存在或複製失敗.
     */
    public boolean copy(byte[] source){
        try {
            Files.write(this.filePath, source, StandardOpenOption.CREATE);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 將來源複製至此.
     *
     * @param source 來源目標，String類別.
     * @param charset 字串寫入編碼，例:StandardCharsets.UTF_8;.
     * @return true複製檔案成功; false檔案不存在或複製失敗.
     */
    public boolean copy(String source, Charset charset){
        try {
            Files.write(this.filePath, source.getBytes(charset), StandardOpenOption.CREATE);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * 將來源複製至此.
     *
     * @param source 來源目標，InputStream類別.
     * @return true複製檔案成功; false檔案不存在或複製失敗.
     */
    public boolean copy(InputStream source){
        try {
            Files.copy(source, this.filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 寫入至檔案末端.
     * @param data 寫入資料來源，ByteArray類別.
     * @return true寫入檔案成功; false檔案不存在或寫入失敗.
     */
    public boolean write(byte[] data){
        try {
            Files.write(this.filePath, data, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    /**
     * 寫入至檔案末端.
     * @param data 寫入資料來源，String類別.
     * @param charset 字串寫入編碼，例:StandardCharsets.UTF_8;.
     * @return true寫入檔案成功; false檔案不存在或寫入失敗.
     */
    public boolean write(String data, Charset charset){
        byte[] bArray = data.getBytes(charset);
        try {
            Files.write(this.filePath, bArray, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 讀取檔案至String
     * @param charset 編碼類型，例:StandardCharsets.UTF_8;.
     * @return 目標檔案資料.
     */
    public String readAsString(Charset charset){
        try {
            byte[] encoded = Files.readAllBytes(this.filePath);
            return new String(encoded, charset);
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 讀取檔案至Bytes.
     * @return 目標檔案資料.
     */
    public byte[] readAsByte(){
        try {
            return Files.readAllBytes(this.filePath);
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
