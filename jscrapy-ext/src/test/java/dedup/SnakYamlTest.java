package dedup;

import org.testng.annotations.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Created by cxu on 2017/1/16.
 */
public class SnakYamlTest {

    @Test
    public void test() {
//        JscrapyConfig jscrapyConfig = new JscrapyConfig();
//        TaskBaseConfig taskBaseConfig = new TaskBaseConfig();
//        taskBaseConfig.setTaskName("test");
//        taskBaseConfig.setTaskId("test-id");
//        jscrapyConfig.setTaskBaseConfig(taskBaseConfig);
//
//        TaskComponentConfig taskComponentConfig = new MongoDedepConfig();
//        MongoDedepConfig m = (MongoDedepConfig)taskComponentConfig;
//        m.setDbName("mongpodb_name");
//        m.setPort(20338);
//        jscrapyConfig.setTaskComponentConfig(ComponentName.DEDUP_H2, m);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setCanonical(false);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);

        Yaml yaml = new Yaml(options);
//        String s = yaml.dump(jscrapyConfig);

//        System.out.print(s);
    }
}
