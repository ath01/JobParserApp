package pro.asfert.jobparser.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
                    subCategoryMap.put(categoryLink, categoryName);
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
                /* Проходим по списку подкатегорий, ищем ссылки на дополнительные страницы, если они существуют, добавляем во
            временный список след.страниц
             */
                try {
                    nextPage = doc.select("span[class=pga-forward-link]").first();
                    nextPagecat = nextPage.select("a");
                    linkToNextPage = nextPagecat.get(0).attr("href");
                } catch (Exception e) {
                    /*NOP*/
                }
                if (linkToNextPage != null) {
                    StringBuilder sb = new StringBuilder("http://rabota66.ru");
                    sb.append(linkToNextPage);
                    linkToNextPage = sb.toString();
                    nextPagesLinks.add(linkToNextPage);
                }

                /* проходим по списку*/
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

    public static Map<String, String> getDataForDBbyOneURL(String url) {
        Map<String, String> getDataForDBbyOneURL = new HashMap<String, String>();
        Document doc;
        Element vacancy;
        String vacancyString;
        Element salary;
        String salaryString;
        Element experience;
        String experienceString;
        Element education;
        String educationString;
        Element employer;
        String employerString;
        Element details;
        String detailsString = "";

        try {
            doc = Jsoup.connect(url).get();

            vacancy = doc.select("div.title-").first();
            vacancyString = vacancy.text().trim();

            experience = doc.select("b.salary-").first();
            experienceString = experience.text();

            salary = doc.select("div.vvloa-box").first();
            salaryString = salary.select("dd").first().text();

            education = doc.select("div.vvloa-box").first();
            educationString = education.select("dd").last().text();

            employer = doc.select("div.employer-").first();
            employerString = employer.select("a").text();

            details = doc.select("div.details-").first();
            Elements br = details.select("br");
            detailsString+=details.select("h2").first().nextSibling().toString();
            for (int i = 0; i < br.size(); i++) {
                        detailsString += br.get(i).nextSibling().toString();
            }

            getDataForDBbyOneURL.put("vacancy", vacancyString);
            getDataForDBbyOneURL.put("salary", salaryString);
            getDataForDBbyOneURL.put("experience", experienceString);
            getDataForDBbyOneURL.put("education", educationString);
            getDataForDBbyOneURL.put("employer", employerString);
            getDataForDBbyOneURL.put("details ", detailsString);
        } catch (IOException e) {
            /*NOP*/
        }


        return getDataForDBbyOneURL;
    }

    /*public static void main(String[] args) {
        String url = "http://www.rabota66.ru/vacancy/256509";
        Map<String, String> getDataForDBbyOneURL = getDataForDBbyOneURL(url);
    }*/



}
