package pro.asfert.jobparser.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by darkwawe on 06.03.2015.
 */
public class Parser {
    static Map<String, String> categoryMap = new TreeMap<String, String>();
    static Map<String, String> subCategoryMap = new TreeMap<String, String>();
    static Map<String, String> vacancyMap = new TreeMap<String, String>();

    /*  СТАТИЧЕСКИЙ МЕТОД, СОБИРАЮЩИЙ НАЗВАНИЯ КАТЕГОРИЙ И ССЫЛКИ НА НИХ*/
    public static Map<String, String> getCategoryMap() {

        Document doc;
        Elements category;
        Element content;

        try {
            doc = Jsoup.connect("http://rabota66.ru").get();
            content = doc.getElementById("categoryTreeIndex");
            category = content.select("a");
            for (int i = 0; i < category.size(); i++) {
                String categoryLink = category.get(i).attr("href");
                String categoryName = category.get(i).text();
                if (categoryName.matches("^\\D*$")) {
                    StringBuilder sb = new StringBuilder("http://rabota66.ru");
                    sb.append(categoryLink);
                    categoryLink = sb.toString();
                    categoryMap.put(categoryLink, categoryName);
                }
            }

        } catch (IOException e) {
            /*NOP*/
        }
        return categoryMap;
    }

    /*  СТАТИЧЕСКИЙ МЕТОД, СОБИРАЮЩИЙ НАЗВАНИЯ ПОДКАТЕГОРИЙ И ССЫЛКИ НА НИХ*/
    public static Map<String, String> getSubCategoryMap() {
        Map<String, String> CategoryTmpMap = getCategoryMap();
        Document doc;
        Element content;
        Elements category;

        try {
            for (Map.Entry<String, String> pair : CategoryTmpMap.entrySet()) {

                doc = Jsoup.connect(pair.getKey()).get();
                content = doc.select("table[class=category-level]").first();
                category = content.select("a");
                for (int i = 0; i < category.size(); i++) {
                    String categoryLink = category.get(i).attr("href");
                    String categoryName = category.get(i).text();
                    StringBuilder sb = new StringBuilder("http://rabota66.ru");
                    sb.append(categoryLink);
                    categoryLink = sb.toString();
                    subCategoryMap.put(categoryLink,categoryName);
                }
            }
        } catch (IOException e) {
            /*NOP*/
        }
        return subCategoryMap;
    }

    /*  СТАТИЧЕСКИЙ МЕТОД, СОБИРАЮЩИЙ НАЗВАНИЯ ВАКАСИЙ ИЗ ПОДКАТЕГОРИЙ И ССЫЛКИ НА НИХ*/
    public static Map<String, String> getVacancyMap() {
        Map<String, String> SubCategoryTmpMap = getSubCategoryMap();
        ArrayList<String> nextPagesLinks = new ArrayList<String>();
        Document doc;
        Element content;
        Elements category;
        Element nextPage;
        Elements nextPagecat;
        int k = 0;

        try {
            for (Map.Entry<String, String> pair : SubCategoryTmpMap.entrySet()) {
                doc = Jsoup.connect(pair.getKey()).get();
                content = doc.getElementById("vacancy-list");
                category = content.select("a");
                String linkToNextPage = null;
                try {
                    nextPage = doc.select("span[class=pga-forward-link]").first();
                    nextPagecat = nextPage.select("a");
                    linkToNextPage = nextPagecat.get(0).attr("href");
                } catch (Exception e) {
                    /*NOP*/
                }
                if (linkToNextPage!=null) {
                    StringBuilder sb = new StringBuilder("http://rabota66.ru");
                    sb.append(linkToNextPage);
                    linkToNextPage = sb.toString();
                    nextPagesLinks.add(linkToNextPage);
                }
                for (int i = 0; i < category.size(); i++) {
                    String categoryLink = category.get(i).attr("href");
                    String categoryName = category.get(i).text();
                    StringBuilder sb = new StringBuilder("http://rabota66.ru");
                    sb.append(categoryLink);
                    categoryLink = sb.toString();
                    if (!categoryName.isEmpty() && !categoryName.contains("в избранное") && categoryLink.contains("vacancy")) {
                        vacancyMap.put(categoryLink, categoryName);
                    }
                }
                k++;
                if (k == 2) {
                    break;
                }
            }
            for (String page : nextPagesLinks) {
                if (nextPagesLinks.isEmpty()) {
                    break;
                }
                doc = Jsoup.connect(page).get();
                content = doc.getElementById("vacancy-list");
                category = content.select("a");
                for (int i = 0; i < category.size(); i++) {
                    String categoryLink = category.get(i).attr("href");
                    String categoryName = category.get(i).text();
                    StringBuilder sb = new StringBuilder("http://rabota66.ru");
                    sb.append(categoryLink);
                    categoryLink = sb.toString();
                    if (!categoryName.isEmpty() && !categoryName.contains("в избранное") && categoryLink.contains("vacancy")) {
                        vacancyMap.put(categoryLink, categoryName);
                    }
                }
            }
        } catch (IOException e) {
            /*NOP*/
        }
        return vacancyMap;
    }


}
