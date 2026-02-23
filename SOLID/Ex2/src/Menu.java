import java.util.LinkedHashMap;
import java.util.Map;

public class Menu {
    private static Map<String, MenuItem> menu;

    Menu() {
        menu = new LinkedHashMap<>();
    }

    public Map<String, MenuItem> getMenu() {
        return menu;
    }

    public void addMenuItem(MenuItem item) {
        getMenu().put(item.id, item);
    }
}
