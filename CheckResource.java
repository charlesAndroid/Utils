import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * com.charles.sample.CheckResource
 *
 * @author Just.T
 * @since 17/3/14
 */
public class CheckResource {
    private static Map<String, Integer> count = new HashMap<>();

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        System.out.println("File path:" + path);
        File file = new File(path);
        checkDir(file);
        try {
            if (file.exists() && file.isDirectory()) {
                checkDir(file);
            } else {
                System.out.println("File not Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String key : count.keySet()) {
            if (count.get(key) > 2) {
                System.out.println("---------------------------");
                System.out.println("layoutId: " + key);
            }
        }

    }

    private static void checkDir(File f) {
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                checkDir(file);
            } else {
                checkFile(file);
            }
        }
    }

//    private static void getStrings() {
//        String str = "rrwerqq84461376qqasfdasdfrrwerqq84461377qqasfdasdaa654645aafrrwerqq84461378qqasfdaa654646aaasdfrrwerqq84461379qqasfdasdfrrwerqq84461376qqasfdasdf";
//        Pattern p = Pattern.compile("qq(.*?)qq");
//        Matcher m = p.matcher(str);
//        ArrayList<String> strs = new ArrayList<String>();
//        while (m.find()) {
//            strs.add(m.group(1));
//        }
//        for (String s : strs) {
//            System.out.println(s);
//        }
//    }


    private static void checkFile(File file) {
        String parent = file.getParentFile().getName();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                index++;
                getResourceString(line, parent + "/" + file.getName() + "--->" + index);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String REGEX = "R\\.layout\\.[a-zA-Z_]{1,}";
    private static final Pattern pattern = Pattern.compile(REGEX);

    private static String getResourceString(String str, String path) {
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            save(group, path);
        }
        return str;
    }


    private static void save(String resourceString, String path) {
        if (resourceString != null && resourceString.length() > 0) {
            if (count.containsKey(resourceString)) {
                int i = count.get(resourceString);
                count.put(resourceString, i + 1);
            } else {
                count.put(resourceString, 1);
            }
        }
    }


}
