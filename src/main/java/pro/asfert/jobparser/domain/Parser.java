package pro.asfert.jobparser.domain;

import org.jsoup.Connection.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
                if (k == 1) {
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

    /* СТАТИЧЕСКИЙ МЕТОД, ПОДГОТАВЛИВАЮЩИЙ КАРТУ ДЛЯ ПОЛЕЙ БАЗЫ ДАННЫХ СО ЗНАЧЕНИЯМИ ПО СТРОКЕ URL*/
    public static Map<String, String> getDataForDBbyOneURL(String url) {
        Map<String, String> getDataForDBbyOneURL = new HashMap<String, String>();
        Document doc;
        Document doc2;
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
        Element hr;
        String hrString;

        String username = "spvelichko@mail.ru";
        String password = "rfcgth18";

        try {
            /*doc = Jsoup
                    .connect(url)
                    .header("Authorization", "Basic " + base64login)
                    .get();*/

            Response res = Jsoup.connect("http://www.rabota66.ru/login")
                    .data("login", username, "password", password)
                    .method(Method.POST)
                    .execute();

            doc2 = res.parse();
            String sessionId = res.cookie("c98ef47ea9405440a4c74543704ba279");
            String abc = sessionId;
            Map<String, String> loginCookies = res.cookies();

            doc = Jsoup.connect(url)
                    .cookies(loginCookies)
                    .get();

            vacancy = doc.select("div.title-").first();
            vacancyString = vacancy.text().trim();

            salary = doc.select("b.salary-").first();
            salaryString = salary.text();

            experience = doc.select("div.vvloa-box").first();
            experienceString = experience.select("dd").first().text();

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
            hr = doc.select("div.vvloa-box").first().nextElementSibling();
            hrString = hr.text();

            getDataForDBbyOneURL.put("vacancy", vacancyString.trim());
            getDataForDBbyOneURL.put("salary", salaryString.trim());
            getDataForDBbyOneURL.put("experience", experienceString.trim());
            getDataForDBbyOneURL.put("education", educationString.trim());
            getDataForDBbyOneURL.put("employer", employerString.trim());
            getDataForDBbyOneURL.put("details", detailsString.trim());
            getDataForDBbyOneURL.put("hr", hrString.trim());
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
