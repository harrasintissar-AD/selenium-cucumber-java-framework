package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import driver.DriverFactory;

public class ScreenshotUtil {

    public static String captureScreenshot(
            String fileName) {

        try {

            File source =
                    ((TakesScreenshot)
                            DriverFactory.getDriver())
                            .getScreenshotAs(
                                    OutputType.FILE);

            String path =
                    "target/screenshots/"
                            + fileName
                            .replace(" ", "_")
                            + ".png";

            File destination =
                    new File(path);

            destination
                    .getParentFile()
                    .mkdirs();

            Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return path;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}