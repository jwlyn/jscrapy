package util;

import java.io.File;

/**
 * Created by cxu on 2015/10/3.
 */
public class ResourcePathUtils {
    public static String getResourceFileAbsPath(Class clazz, String fileName) {
        File cfgFile = new File(clazz.getResource(fileName).getFile());
        String path = cfgFile.getAbsolutePath();
        return path;
    }
}
