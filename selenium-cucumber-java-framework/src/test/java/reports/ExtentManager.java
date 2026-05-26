package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;
    
    // we can develop this type of report later now we use cucumber report only

//    public static ExtentReports getInstance() {
//
//        if (extent == null) {
//
//            ExtentSparkReporter sparkReporter =
//                    new ExtentSparkReporter(
//                            "target/ExtentReport.html");
//
//            extent = new ExtentReports();
//
//            extent.attachReporter(
//                    sparkReporter);
//        }
//
//        return extent;
//    }
}