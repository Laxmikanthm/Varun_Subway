package pages.MyAccountOverview;

import base.gui.controls.browser.Generic;
import base.gui.controls.browser.SelectBox;
import base.pages.browser.BrowserBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.Cart.ShoppingCartPage;
import pages.CommonMethodsPage;
import util.Common;
import utils.Logz;
import java.util.*;
import java.util.stream.IntStream;

public class ReorderItemsPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private By txtFieldTitle = By.xpath("//div[@class='reorder-items-list']//h2");
    private static By drpSort=By.xpath("//select[@name='sort']");
    private By drpFilter=By.xpath("//select[@name='filter']");
    private By lnkItemNames=By.xpath("//div[@class='transaction-line-views-cell-actionable-name']/a");
    private By itemPrices=By.xpath("//span[@class='transaction-line-views-price-lead']");
    private By purchasedDateInfo=By.xpath("//p[@class='reorder-items-actions-quantity-last-purchased']");
    private By btnAddToCart=By.xpath("//button[@class='reorder-items-actions-add-to-cart']");

    public CommonMethodsPage commonMethodsPage;
    public ShoppingCartPage shoppingCartPage;

    //This method will return the commonMethodsPage driver object
    public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
        return new CommonMethodsPage(driver);
    }

    public ReorderItemsPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getdropdownSort().elementIsDisplayedAndEnabled()?"Reorder Items page is loaded":"Reorder Items page is not loaded");
    }

    private Generic getItemPrices() throws Exception {
        return new Generic(driver,itemPrices,"itemPrices");
    }
    private List<WebElement> getListItemNames()throws Exception {
        return getItemPrices().getWebElements(lnkItemNames,"List of Product Names");
    }
    private List<WebElement> getListItemPrices()throws Exception {
        return getItemPrices().getWebElements(itemPrices,"List of Product Prices");
    }
    private List<WebElement> getPurchasedDateInfo()throws Exception {
        return getItemPrices().getWebElements(purchasedDateInfo,"Purchased Date Info");
    }
    private List<WebElement> getButtonAddToCart()throws Exception {
        return getItemPrices().getWebElements(btnAddToCart,"List of Addtocart buttons");
    }
    private SelectBox getDropdownFilter()throws Exception {
        return new SelectBox(driver,drpFilter,"Filter Drop down");
    }
    private SelectBox getdropdownSort()throws Exception {
        return new SelectBox(driver,drpSort,"Sort Drop down");
    }

    public void verifyDropDownSort(String name)throws Exception
    {
    	List<WebElement> lstItemNamesEles = getListItemNames();
        if (lstItemNamesEles.size() > 2) {
            if (name.equalsIgnoreCase("By Name")) {
                verifySortByName(name);
            } else if (name.equalsIgnoreCase("By Price")) {
                verifySortByPrice(name);
            } else if (name.equalsIgnoreCase("By Most Recently Purchased")) {
                VerifySortByRecentlyPurchased(name);
            }
        } else {
            Logz.error("No of ReorderItems Found:" + lstItemNamesEles.size() + "is less than 2");
        }
    }

    private void verifySortByName(String name)throws Exception
    {
        //Getting Product names before clicking on filter Option
        List<WebElement> itemnames=getListItemNames();
        String names[]=new String[itemnames.size()];
        int k=0;
        for(int i=0;i<itemnames.size();i++)
        {
            names[k++]=itemnames.get(i).getText();
        }
        getdropdownSort().selectFromDropDown(name);
        WebDriverWait wait=new WebDriverWait(driver,90);
        wait.until(ExpectedConditions.visibilityOfElementLocated(itemPrices));
        //Getting Product names After clicking on filter
        List<WebElement> Filtereditemnames=getListItemNames();
        int filteredItemNameCnt = Filtereditemnames.size();
        String filterednames[]=new String[filteredItemNameCnt];
        int l=0;
        for(int i=0;i<filteredItemNameCnt;i++) filterednames[l++]=Filtereditemnames.get(i).getText();
        commonMethodsPage=gotoCommonMethodsPage();
        Assert.assertEquals(filterednames,commonMethodsPage.sortedResults(names,"Asc"));
        Logz.step("Verified Filtered Results based on SortByName");
    }

    private Double[] descOrder(List<Double> li)
    {
        Double[] doubleValue = li.toArray(new Double[li.size()]);
        IntStream.range(0,doubleValue.length-1).filter(i -> doubleValue[i] < doubleValue[i+1]).forEachOrdered(i -> {
            doubleValue[i] = doubleValue[i] + doubleValue[i+ 1];
            doubleValue[i+ 1] = doubleValue[i] - doubleValue[i+1];
            doubleValue[i] = doubleValue[i] - doubleValue[i+1];
        });        

        return doubleValue;
    }

    private void verifySortByPrice(String name) throws Exception
    {
        SelectBox selectSortType = getdropdownSort();
        WebElement elmSort = selectSortType.getControl();
        selectSortType.selectFromDropDown(name);
        Common.waitForStale(driver,elmSort);
        //Getting Price details after clicking on filtered option
        List<WebElement> filteredItemPrices=getListItemPrices();
        Double newPrice = null;
        List<Double> priceList = new ArrayList<Double>();
        for(WebElement filteredItemPrice : filteredItemPrices)
        {
           // filteredItemPrice.getText().replace("$","");
            newPrice = Double.parseDouble(filteredItemPrice.getText().replace(",","").replace("$",""));
            priceList.add(newPrice);
        }

        Double filteredItemPrice[] = descOrder(priceList);
        commonMethodsPage=gotoCommonMethodsPage();
        Assert.assertEquals(filteredItemPrice,commonMethodsPage.sortedResults(filteredItemPrice,"Desc"));
        Logz.step("Verified Filtered Results based on SortByPrice");
    }

    private Date[] getPurchasedDates()throws Exception
    {
        List<WebElement> datesinfo=getPurchasedDateInfo();
        String info[]=new String[2];
        Date pdates[]=new Date[datesinfo.size()];
        int k=0;
        for(int i=0;i<datesinfo.size();i++)
        {
            info=datesinfo.get(i).getText().split("on ");
            pdates[k++]=new Date(info[1]);
        }
        return pdates;
    }

    private void VerifySortByRecentlyPurchased(String name) throws Exception
    {
        SelectBox selectSortType = getdropdownSort();
        WebElement elmSort = selectSortType.getControl();
        selectSortType.selectFromDropDown(name);
        Common.waitForStale(driver,elmSort);
        Date[] purchasedDates=getPurchasedDates();
        commonMethodsPage=gotoCommonMethodsPage();
        Assert.assertEquals(purchasedDates,commonMethodsPage.sortedResults(purchasedDates,"Desc"));
        Logz.step("Verified Filtered Results based on SortByRecentlyPurchased");
    }

    public void verifyFilterByDays(String name)throws Exception
    {
        Boolean status=false;
        if(name.equalsIgnoreCase("Show last 15 days"))
        {
            status=VerifyReorderItemsFilterByDays(name,15);
        }
        else if(name.equalsIgnoreCase("Show last 30 days"))
        {
            status=VerifyReorderItemsFilterByDays(name,30);
        }
        else if(name.equalsIgnoreCase("Show last 60 days"))
        {
            status=VerifyReorderItemsFilterByDays(name,60);
        }
        else if(name.equalsIgnoreCase("Show last 90 days"))
        {
            status=VerifyReorderItemsFilterByDays(name,90);
        }
        else if(name.equalsIgnoreCase("Show last 180 days"))
        {
            status=VerifyReorderItemsFilterByDays(name,180);
        }
        if(status.equals(true))
        {
            Logz.step("Verified Filtered Results based on"+name);
        }
        else Logz.step("Filtered Results based on"+name+"is failed");
    }

    private Boolean VerifyReorderItemsFilterByDays(String name,int n) throws Exception
    {
        Boolean flag=false;
        Calendar cal=Calendar.getInstance();
        SelectBox selectLastDays = getDropdownFilter();
        WebElement elmLastDays = selectLastDays.getControl();
        selectLastDays.selectFromDropDown(name);
        if(!name.equals("Show last 15 days"))
        {
            Common.waitForStale(driver,elmLastDays);
        }
        Date[] purchasedDates=getPurchasedDates();
        cal.add(Calendar.DAY_OF_MONTH,-n);
        Date previousDate=cal.getTime();
        for(int i=0;i<purchasedDates.length;i++)
        {
            if(purchasedDates[i].compareTo(previousDate)>=0)
            {
                flag=true;
            }
            else
            {
                flag=false;
                break;
            }
        }
        return flag;
    }
    
	// Adding n number of items to the cart
	public List<String> addToCartFromReorderItemsPage(int n) throws Exception {
		List<WebElement> lstItemNamesEles = getListItemNames();
		int itemNamesCnt = lstItemNamesEles.size();
		List<WebElement> btnAddCartEles = getButtonAddToCart();
		Assert.assertEquals(itemNamesCnt, btnAddCartEles.size(), "Every Item is not having Add to card button");
		// Adding reordered itemsto the cart
		Logz.step("after assert");
		List<String> names = new ArrayList<>();
		for (int i = 0; i < itemNamesCnt; i++) {
			names.add(lstItemNamesEles.get(i).getText());
			btnAddCartEles.get(i).click();
			if (i == (n - 1)) break;
		}
		return names;
	}

    public ShoppingCartPage enterShoppingPage() throws Exception {
   	    Logz.step("xxxxxx method");
		return new ShoppingCartPage(driver);
    }
}
