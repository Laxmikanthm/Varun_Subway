package base;

import base.test.BaseTest;
import execution.platform.Executors;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.winium.WiniumDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import testManagement.TFS.TFSClient;
import utils.Logz;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by E001530 on 12/1/2017.
 */
public class EOSWebContext extends BaseTest {
    public String driverName = null, file_path;
    ApplicationContext context,app_context;
    public HashMap<String,String> mapBundleFile;
    private HashMap<String,String> mapEnvironment;
    Boolean flag = Boolean.parseBoolean(System.getProperty("tfsUpdate"));
    public static Map<String, RemoteWebDriver> drivers;
    TFSClient tfsClient;
    public EOSWebContext() {
    }

    public EOSWebContext(String Xml_file_path)
    {
        this.file_path = Xml_file_path;
        //LoadTestBundleValues();
//        LoadEnvironment();
    }

    @BeforeSuite(alwaysRun = true)
    public void setupSuite1(ITestContext testContext) throws Exception {
//        context = new FileSystemXmlApplicationContext("src/test/resources/EOSBeans.xml");
//        System.out.println("NOOOOO Executors fetched...");
//        executors = (Executors) context.getBean("executors");
//        System.out.println("Executors fetched...");
        //driverName = testContext.getCurrentXmlTest().getParameter("driverName");
        //LoadTestBundleValues();
        if (flag) {
            tfsClient = new TFSClient(System.getProperty("tFSPlanId"), System.getProperty("tFSSuiteId"));
            tfsClient.createTestRun(System.getProperty("tFSRunName"));
        }
//        LoadEnvironment();
    }
    @BeforeMethod
    public void getDriverName(ITestContext testContext) throws Exception {
        driverName = testContext.getCurrentXmlTest().getParameter("driverName");
    }

    @Override
    @AfterMethod
    protected void methodTearDown(ITestResult result) throws IOException {

        if (flag) {

            List<String> testCaseId = new ArrayList<>();
            String[] parseTestCaseId = result.getMethod().getMethodName().split("_");
            for (int i = 1; i < parseTestCaseId.length; i++) {
                testCaseId.add(parseTestCaseId[i]);
            }
            for (int j = 0; j < testCaseId.size(); j++) {
                String status = "Failed";
                if (result.isSuccess()) {
                    status = "Passed";
                }
                try {
                    tfsClient.updateResult(testCaseId.get(j), tfsClient.getTestPointId(testCaseId.get(j)), status);

                } catch (Exception e) {

                }
            }
        }
        drivers = (Map)driverThread.get();
        drivers.forEach((k, v) -> {
            if (!(v instanceof WiniumDriver) && v != null) {
                if (System.getProperty("spring.profiles.active").equalsIgnoreCase("sauce")) {
                    this.updateSauceLabTestStatus(result, v);
                }

                Logz.info("Quitting driver: " + k);
                v.quit();
            }

        });
        Logz.endTestCase(result);
    }


    public ApplicationContext getApp_context() throws Exception {
        try{
        if(this.file_path!=null){
            app_context=new ClassPathXmlApplicationContext(this.file_path);
        }else
            return null;
        return app_context;
        }catch (Exception ex){
            throw new Exception("Bean file does not exist " + ex.getMessage());
        }
    }

    public Bean getBean(ApplicationContext appContext, String BeanName){

        return (Bean)appContext.getBean(BeanName);
    }

    //getting test resource bundle values
    public  void LoadTestBundleValues(){
        mapBundleFile = new HashMap<String,String>();
//Logz.step("User Directory:::" +System.getProperty("user.dir") );
        String config = System.getProperty("user.dir")+"\\src\\main\\resources\\ApplicationLocale.properties";
        Properties prop = new Properties();
        try
        {
            InputStream inputStream = new FileInputStream(config);
            prop.load(inputStream);

            Enumeration<String> enums = (Enumeration<String>) prop.propertyNames();
            while (enums.hasMoreElements()) {
                String key = enums.nextElement();
                String value = prop.getProperty(key);
                mapBundleFile.put(key.toString(), value.toString());
            }
//Logz.step("User Directory:::" +mapBundleFile.size() );
            //            Set<String> keys = prop.stringPropertyNames();
//            for (String key : keys) {
//                String value = prop.getProperty(key);
//
//                mapBundleFile.put(key.toString(), value.toString());
//            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, String> getMapBundleFile()
    {
        return mapBundleFile;
    }

    public void setMapBundleFile(HashMap<String, String> hmBundleFile)
    {
        this.mapBundleFile = hmBundleFile;
    }

    public HashMap<String, String> getMapEnvironment()
    {
        return mapEnvironment;
    }

    public void setHmEnvironment(HashMap<String, String> mapEnvironment)
    {
        this.mapEnvironment = mapEnvironment;
    }
}
