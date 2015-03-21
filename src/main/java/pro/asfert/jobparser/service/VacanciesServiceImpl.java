package pro.asfert.jobparser.service;

import pro.asfert.jobparser.domain.Parser;

import java.util.HashMap;
import java.util.Map;

public class VacanciesServiceImpl implements VacanciesService {
    Map<String, String> DataMap = Parser.getVacancyMap();
    Map<String, String> hashmap = new HashMap<String, String>();

    @Override
    public String LoadDataBase() {
        String query = "";
        for (String s : DataMap.keySet()) {
            hashmap = Parser.getDataForDBbyOneURL(s);
            for (Map.Entry<String, String> pair : hashmap.entrySet()) {
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }

            query = "INSERT INTO Vacancies (vacancy, salary, experience, education, employer, details, hr, url)" +
                    " VALUES (" + "\'" + hashmap.get("vacancy") + "\'" + ", " + "\'" + hashmap.get("salary") + "\'" + ", " + "\'" + hashmap.get("experience") + "\'" + ", " + "\'" + hashmap.get("education") + "\'" + ", " +
                    "\'" + hashmap.get("employer") + "\'" + ", " + "\'" + hashmap.get("details") + "\'" + ", " + "\'" + hashmap.get("hr") + "\'"+ ", " + "\'" + hashmap.get("url") + "\'" + ")";

        }
        return query;
    }

    @Override
    public void FindVacancy(String query) {

    }
}
