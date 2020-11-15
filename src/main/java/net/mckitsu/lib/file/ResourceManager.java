package net.mckitsu.lib.file;

import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Resource管理器.
 *
 * @author  ZxyKira
 */
public class ResourceManager extends FileManager{

    /**
     * 建構子.
     * <p>ResourceManager resourceManager = new ResourceManager(ClassLoader.getSystemClassLoader(), "", "config.yml")</p>
     *
     * @param classLoader classLoader, Object.class.getClassloader();.
     * @param dirPath 檔案路徑.
     * @param fileName 檔案名稱.
     * @throws URISyntaxException .
     */
    public ResourceManager(ClassLoader classLoader, String dirPath, String fileName) throws URISyntaxException {
        super(Objects.requireNonNull(classLoader.getResource(dirPath)).toURI(), fileName);
    }

    /**
     * 建構子.
     * <p>ResourceManager resourceManager = new ResourceManager(ClassLoader.getSystemClassLoader(), "config.yml")</p>
     *
     * @param classLoader classLoader, Object.class.getClassloader();.
     * @param fileName 檔案名稱.
     * @throws URISyntaxException .
     */
    public ResourceManager(ClassLoader classLoader, String fileName) throws URISyntaxException {
        this(classLoader, "", fileName);
    }
}
