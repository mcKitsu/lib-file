package net.mckitsu.lib.file;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 目錄管理器.
 *
 * @author  ZxyKira
 */
public class FolderManager {
    private final Path dirPath;

    public FolderManager(String dirPath){
        this.dirPath = Paths.get(dirPath);
    }

    public FolderManager(URI dirPath){
        this.dirPath = Paths.get(dirPath);
    }

    /**
     * 取得目錄中的所有檔案與目錄列表.
     *
     * @return 檔案與目錄列表.
     */
    public String[] list(){
        try {
            Path[] cache = Files.list(this.dirPath).toArray(Path[]::new);
            String[] result = new String[cache.length];

            for(int i=0; i<cache.length; i++){
                String srcPath = cache[i].toString();
                int fileS = srcPath.lastIndexOf('\\');
                result[i] = srcPath.substring(fileS + 1);
            }
            return result;

        } catch (IOException e) {
            return new String[0];
        }
    }

    public FileManager[] getFiles(){
        String[] list = this.list();
        FileManager[] result = new FileManager[list.length];

        for(int i=0; i<list.length; i++)
            result[i] = new FileManager(this, list[i]);

        return result;
    }

    /**
     * 建立所有不存在的父目錄.
     * @return true建立成功; false建立失敗或已存在.
     */
    public boolean createDir(){
        try {
            Files.createDirectories(this.dirPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 取得目錄path
     * @return 目錄path
     */
    public String getDirPath(){
        return dirPath.toString();
    }

    /**
     *
     * @return 目錄path
     */
    public String toString(){
        return getDirPath();
    }
}
