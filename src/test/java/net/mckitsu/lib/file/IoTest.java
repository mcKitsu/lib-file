package net.mckitsu.lib.file;

import net.mckitsu.lib.file.resource.yaml.Config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class IoTest {
    public static boolean test(){
        System.out.println("Start io test");
        System.out.println("\n<------------------------------------------------------------------->");
        if(!testResourceManager())
            return false;

        System.out.println("\n<------------------------------------------------------------------->");
        if(!testYamlManager())
            return false;

        System.out.println("\n<------------------------------------------------------------------->");
        System.out.println("End of io test\n");
        return true;
    }

    private static boolean testResourceManager(){
        System.out.format("Begin test %s\n", ResourceManager.class.getName());
        try {
            ResourceManager cfg =
                    new ResourceManager(ClassLoader.getSystemClassLoader(), "",  "config.yml");
            System.out.println(cfg.read.asString(StandardCharsets.UTF_8));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.format("Test failed %s\n", ResourceManager.class.getName());
            return false;
        }
        System.out.format("Test successful %s\n", ResourceManager.class.getName());
        return true;
    }

    private static boolean testYamlManager(){
        System.out.format("Begin test %s\n", YamlManager.class.getName());

        try {
            ResourceManager file = new ResourceManager(ClassLoader.getSystemClassLoader(), "",  "config.yml");
            YamlManager<Config> loadYaml = new YamlManager<>(Config.class);
            Config config = loadYaml.load(file);
            System.out.println(config);

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            System.out.format("Test failed %s\n", YamlManager.class.getName());
        }

        System.out.format("Test successful %s\n", YamlManager.class.getName());
        return true;
    }
}
