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

    public final Copy copy = new Copy() {
        @Override
        protected Path getFilePath() {
            return FileManager.this.filePath;
        }
    };

    public final Write write = new Write() {
        @Override
        protected Path getFilePath() {
            return FileManager.this.filePath;
        }
    };

    public final Read read = new Read() {
        @Override
        protected Path getFilePath() {
            return FileManager.this.filePath;
        }
    };

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
     * 檔案管理器.複製
     * @author  ZxyKira
     */
    public static abstract class Copy {
        protected abstract Path getFilePath();

        /**
         * 將來源複製至此.
         *
         * @param source 來源目標，FileManager類別.
         * @return true複製檔案成功; false檔案不存在或複製失敗.
         */
        public boolean asFileManager(FileManager source){
            try {
                Files.copy(source.filePath, getFilePath(),  StandardCopyOption.REPLACE_EXISTING);
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
        public boolean asByte(byte[] source){
            try {
                Files.write(getFilePath(), source, StandardOpenOption.CREATE);
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
        public boolean asString(String source, Charset charset){
            try {
                Files.write(getFilePath(), source.getBytes(charset), StandardOpenOption.CREATE);
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
        public boolean asInputStream(InputStream source){
            try {
                Files.copy(source, getFilePath(), StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    /**
     * 檔案管理器.寫入至檔案末端.
     *
     * @author  ZxyKira
     */
    public static abstract class Write{
        protected abstract Path getFilePath();

        /**
         * 寫入至檔案末端.
         * @param data 寫入資料來源，ByteArray類別.
         * @return true寫入檔案成功; false檔案不存在或寫入失敗.
         */
        public boolean asByte(byte[] data){
            try {
                Files.write(getFilePath(), data, StandardOpenOption.APPEND);
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
        public boolean asString(String data, Charset charset){
            byte[] bArray = data.getBytes(charset);
            try {
                Files.write(getFilePath(), bArray, StandardOpenOption.APPEND);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    /**
     * 檔案管理器.讀取檔案.
     *
     * @author  ZxyKira
     */
    public static abstract class Read{
        protected abstract Path getFilePath();

        /**
         * 讀取檔案至String
         * @param charset 編碼類型，例:StandardCharsets.UTF_8;.
         * @return 目標檔案資料.
         */
        public String asString(Charset charset){
            try {
                byte[] encoded = Files.readAllBytes(getFilePath());
                return new String(encoded, charset);
            } catch (IOException e) {
                return "";
            }
        }

        /**
         * 讀取檔案至Bytes.
         * @return 目標檔案資料.
         */
        public byte[] asBytes(){
            try {
                return Files.readAllBytes(this.getFilePath());
            } catch (IOException e) {
                return new byte[0];
            }
        }
    }
}
