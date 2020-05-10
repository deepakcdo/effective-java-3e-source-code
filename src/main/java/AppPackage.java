import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AppPackage {

    public static void main(String[] args) {

        try {
            String path = "/Users/deepakcdo/Documents/MySpace/Dev/corejava/src/main/java/";
            walk((path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void walk(String path) {

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
                System.out.println("Dir:" + f.getAbsoluteFile());
            } else {
                if (f.getName().endsWith(".java")) {
                    addPackageToClass(f.getAbsolutePath());
                }
            }
        }
    }

    private static void addPackageToClass(String file) {
        try {
            List<String> strings = Files.readAllLines(
                    Paths.get(file));
            String packageName = getPackageName(file);
            String firstLine = strings.get(0);
            replaceKeyWord("package", strings, firstLine, packageName);
//            replaceKeyWord("module", strings, firstLine, packageName);
              Files.write(Paths.get(file), strings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replaceKeyWord(String keyWord, List<String> strings, String firstLine, String packageName){
        if (firstLine.startsWith(keyWord) ){
            strings.remove(0);
            strings.add(0, keyWord + " " + packageName + ";" + System.lineSeparator());
        }
    }

    private static String getPackageName(String fileName) {
        String removeSufix = fileName.replace("/Users/deepakcdo/Documents/MySpace/Dev/corejava/src/main/java/", "");
        String[] split = removeSufix.split("/");
        String returnValue = "";
        for (int i = 0; i < (split.length - 1); i++) {
            returnValue = returnValue + split[i] + ".";
        }
        returnValue = returnValue.substring(0, returnValue.length() - 1);
        return returnValue;

    }
}
