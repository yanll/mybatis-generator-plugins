package tk.techforge.tools.mybatis.generator;


import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Gen {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        String genCfg = "/mybatis-generator.xml";
        URL url = Gen.class.getResource(genCfg);
        String file = url.getFile();
        File configFile = new File(file);
        ConfigurationParser cfgParser = new ConfigurationParser(warnings);
        Configuration config = cfgParser.parseConfiguration(configFile);

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
        generator.generate(null);
        List<GeneratedXmlFile> s = generator.getGeneratedXmlFiles();
        List<GeneratedJavaFile> v = generator.getGeneratedJavaFiles();
        System.out.println("Mybatis 代码生成成功！");

    }
}
