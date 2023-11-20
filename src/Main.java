import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Robot ROBOT;
    private static final int SLEEP_FOR = 180;
    private static boolean IS_CHECK_MOUSEINFO = false;

    private static final Map<String, MousePosition> MOUSE_POSITION_1920x1080_MAP = new HashMap<>();

    static {
        try {
            ROBOT = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        initializeResolutionMap(MOUSE_POSITION_1920x1080_MAP, "1920x1080");
        System.out.println(MOUSE_POSITION_1920x1080_MAP.entrySet());
    }

    public static void main(String[] args) {

        for (int i = 3; i > 0; i--) {
            System.out.println(i + " seconds to focus on the game");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("EXECUTING...");

        openGraphicalSettings();
        setPresetToExtreme();
        setSceneComplexity();
        setRendering();
        saveSettings();

        while (IS_CHECK_MOUSEINFO) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            Point point = pointerInfo.getLocation();
            System.out.println((int) point.getX() + " " + (int) point.getY());
        }

    }

    private static void openGraphicalSettings() {

        mouseClick(1);
        Main.ROBOT.keyPress(KeyEvent.VK_ESCAPE);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("SETTINGS").getX(), MOUSE_POSITION_1920x1080_MAP.get("SETTINGS").getY());
        mouseClick(1);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("VIDEO").getX(), MOUSE_POSITION_1920x1080_MAP.get("VIDEO").getY());
        mouseClick(1);
    }

    private static void setPresetToExtreme() {
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("QUALITY").getX(), MOUSE_POSITION_1920x1080_MAP.get("QUALITY").getY());
        mouseClick(1);
    }

    private static void mouseClick(int timesClicked) {
        for (int i = 0; i < timesClicked; i++) {
            Main.ROBOT.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            sleep();
            Main.ROBOT.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            sleep();
        }
    }

    private static void setSceneComplexity() {
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("OBJECT_DETAIL").getX(), MOUSE_POSITION_1920x1080_MAP.get("OBJECT_DETAIL").getY());
        mouseClick(2);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("TERRAIN_DETAIL").getX(), MOUSE_POSITION_1920x1080_MAP.get("TERRAIN_DETAIL").getY());
        mouseClick(2);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("TEXTURE_DETAIL").getX(), MOUSE_POSITION_1920x1080_MAP.get("TEXTURE_DETAIL").getY());
        mouseClick(2);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("SHADOW_DETAIL").getX(), MOUSE_POSITION_1920x1080_MAP.get("SHADOW_DETAIL").getY());
        mouseClick(2);
    }

    private static void setRendering() {
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("TEXTURE_FILTERING").getX(), MOUSE_POSITION_1920x1080_MAP.get("TEXTURE_FILTERING").getY());
        mouseClick(2);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("TERRAIN_SURFACE_DETAIL").getX(), MOUSE_POSITION_1920x1080_MAP.get("TERRAIN_SURFACE_DETAIL").getY());
        mouseClick(2);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("POSTPROCESS_ANTIALIASING").getX(), MOUSE_POSITION_1920x1080_MAP.get("POSTPROCESS_ANTIALIASING").getY());
        mouseClick(1);

        disableFoliageSmoothing();

        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("HARDWARE_ANTIALIASING").getX(), MOUSE_POSITION_1920x1080_MAP.get("HARDWARE_ANTIALIASING").getY());
        mouseClick(3);

        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("AMBIENT_OCCLUSION").getX(), MOUSE_POSITION_1920x1080_MAP.get("AMBIENT_OCCLUSION").getY());
        mouseClick(3);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("POSTPROCESS_QUALITY").getX(), MOUSE_POSITION_1920x1080_MAP.get("POSTPROCESS_QUALITY").getY());
        mouseClick(0);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("WATER_REFLECTIONS").getX(), MOUSE_POSITION_1920x1080_MAP.get("WATER_REFLECTIONS").getY());
        mouseClick(0);
    }

    private static void disableFoliageSmoothing() {
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("FOLIAGE_SMOOTHING").getX(), MOUSE_POSITION_1920x1080_MAP.get("FOLIAGE_SMOOTHING").getY());
        mouseClick(1);
    }

    private static void saveSettings() {
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("SAVE").getX(), MOUSE_POSITION_1920x1080_MAP.get("SAVE").getY());

        mouseClick(1);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("BACK").getX(), MOUSE_POSITION_1920x1080_MAP.get("BACK").getY());

        mouseClick(1);
        Main.ROBOT.mouseMove(MOUSE_POSITION_1920x1080_MAP.get("CONTINUE").getX(), MOUSE_POSITION_1920x1080_MAP.get("CONTINUE").getY());

        mouseClick(1);
    }

    private static void initializeResolutionMap(Map<String, MousePosition> mousePositionArrayMap, String resolution) {
        ClassLoader classLoader = Main.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(resolution)) {
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",\\s*");
                        if (parts.length == 3) {
                            String setting = parts[0];
                            int x = Integer.parseInt(parts[1]);
                            int y = Integer.parseInt(parts[2]);

                            MousePosition mousePosition = new MousePosition(x, y);
                            mousePositionArrayMap.put(setting, mousePosition);
                        }
                    }
                }
            } else {
                System.out.println("File not found in resources.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(Main.SLEEP_FOR);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}