import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Test {
    public static void main(String[] args) {
        Preferences pre=Preferences.systemNodeForPackage(Test.class);
        pre.put("userName", "******");
        pre.putInt("age", 26);
        try {
            pre.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        Preferences now=Preferences.systemNodeForPackage(Test.class);
        System.out.println(now.get("userName", "为空"));
        System.out.println(now.getInt("age", 0));
    }
}