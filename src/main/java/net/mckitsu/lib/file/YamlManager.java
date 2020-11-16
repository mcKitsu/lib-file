package net.mckitsu.lib.file;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * YAML載入器.
 *
 * @author  ZxyKira
 */
public class YamlManager<T> {
    private final Yaml loadYaml;
    private final Yaml dumpYaml;

    /**
     * 建構子，不指定Class類，以Map輸出
     *
     */
    public YamlManager(){
        final DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        this.loadYaml = new Yaml();
        this.dumpYaml = new Yaml(options);
    }

    /**
     * 建構子
     *
     * @param theRoot 目標結構本體, 如Object.class.
     */
    public YamlManager(Class<?> theRoot){
        final DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        this.loadYaml = new Yaml(new Constructor(theRoot));
        this.dumpYaml = new Yaml(options);
    }

    /**
     * 建構子，CustomClassLoaderConstructor模式.
     *
     * @param theRoot 目標結構本體, 如Object.class
     * @param classLoader Class母體.
     */
    public YamlManager(Class<?> theRoot, ClassLoader classLoader){
        final DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        this.loadYaml = new Yaml(new CustomClassLoaderConstructor(theRoot, classLoader));
        this.dumpYaml = new Yaml(options);
    }

    /**
     * 從YAML字串載入至目標結構.
     *
     * @param fileManager 原始來源資料.
     * @return 目標結構.
     * @throws IOException 結構格式錯誤.
     */
    public T load(FileManager fileManager) throws IOException {
        return load(fileManager.readAsString(StandardCharsets.UTF_8));
    }

    /**
     * 從YAML字串載入至目標結構.
     *
     * @param source 原始來源資料.
     * @return 目標結構.
     * @throws IOException 結構格式錯誤.
     */
    public T load(String source) throws IOException {
        T result;
        try{
            result = this.loadYaml.load(source);
        }catch (YAMLException e){
            throw new IOException(e);
        }
        return result;
    }

    /**
     * 將結構輸出為YAML字串
     *
     * @param object 目標結構0
     * @return YAML字串.
     */
    public String dump(T object){
        return this.dumpYaml.dumpAs(object, Tag.MAP, null);
    }

}
