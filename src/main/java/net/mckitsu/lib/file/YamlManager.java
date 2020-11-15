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
    private Yaml loadYaml;
    private Yaml dumpYaml;

    public final Load<T> load = new Load<T>() {
        @Override
        protected Yaml getLoadYaml() {
            return YamlManager.this.loadYaml;
        }
    };
    public final Dump<T> dump = new Dump<T>() {
        @Override
        protected Yaml getDumpYaml() {
            return YamlManager.this.dumpYaml;
        }
    };

    /**
     * 建構子
     *
     * @param theRoot 目標結構本體, 如Object.class.
     */
    public YamlManager(Class<? extends Object> theRoot){
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
    public YamlManager(Class<? extends Object> theRoot, ClassLoader classLoader){
        final DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        this.loadYaml = new Yaml(new CustomClassLoaderConstructor(theRoot, classLoader));
        this.dumpYaml = new Yaml(options);
    }

    public static abstract class Load<T>{
        protected abstract Yaml getLoadYaml();

        /**
         * 從YAML字串載入至目標結構.
         *
         * @param fileManager 原始來源資料.
         * @return 目標結構.
         * @throws IOException 結構格式錯誤.
         */
        public T asFileManager(FileManager fileManager) throws IOException {
            return asString(fileManager.read.asString(StandardCharsets.UTF_8));
        }

        /**
         * 從YAML字串載入至目標結構.
         *
         * @param source 原始來源資料.
         * @return 目標結構.
         * @throws IOException 結構格式錯誤.
         */
        public T asString(String source) throws IOException {
            T result;
            try{
                result = this.getLoadYaml().load(source);
            }catch (YAMLException e){
                throw new IOException(e);
            }
            return result;
        }
    }

    public static abstract class Dump<T>{
        protected abstract Yaml getDumpYaml();

        /**
         * 將結構輸出為YAML字串
         *
         * @param object 目標結構0
         * @return YAML字串.
         */
        public String asString(T object){
            return this.getDumpYaml().dumpAs(object, Tag.MAP, null);
        }
    }

}
