package skuc.io.skuciocore.utils;

import java.util.Map;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class ClassScannerUtils {

    public static Map<String, String> findAllClasses(String packageName) {
        var reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
          .stream()
          .collect(Collectors.toMap(Class::getSimpleName, Class::getName));
    }

    public static String getWorkingPath() {
        return System.getProperty("user.dir");
    }
}
