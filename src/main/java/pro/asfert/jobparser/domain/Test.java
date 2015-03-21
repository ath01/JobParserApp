package pro.asfert.jobparser.domain;

import java.util.TreeMap;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("*****************************************************");
        System.out.println("Собираем категории вакансий и ссылки на них");
        System.out.println("*****************************************************");
        Map <String,String> treeMap = Parser.getCategoryMap();
        for (Map.Entry<String, String> pair : treeMap.entrySet()) {
            System.out.println(pair.getKey() + " : " + pair.getValue());

        }

        System.out.println();
        System.out.println("*****************************************************");
        System.out.println("Собираем подкатегории вакансий и ссылки на них");
        System.out.println("*****************************************************");
        System.out.println();

        Map<String, String> subCategory = Parser.getSubCategoryMap();
        for (Map.Entry<String, String> pair : subCategory.entrySet()) {
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }

        /*System.out.println();
        System.out.println("*****************************************************");
        System.out.println("Собираем подкатегории вакансий и ссылки на них");
        System.out.println("*****************************************************");
        System.out.println();

        Map<String, String> mapOfSite = Parser.mapOfCategories;
        for (Map.Entry<String, String> pair : mapOfSite.entrySet()) {
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }*/

        System.out.println();
        System.out.println("*****************************************************");
        System.out.println("Собираем вакансии и ссылки на них");
        System.out.println("*****************************************************");
        System.out.println();

        Map<String, String> mapOfSite1 = Parser.getVacancyMap();

        for (Map.Entry<String, String> pair : mapOfSite1.entrySet()) {
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
        System.out.println(mapOfSite1.size());
        System.out.println(Parser.vacancyMap.size());



    }
}
