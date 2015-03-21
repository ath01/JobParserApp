package pro.asfert.jobparser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.asfert.jobparser.dao.VacanciesDAO;
import pro.asfert.jobparser.domain.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class VacanciesServiceImpl implements VacanciesService {

    @Autowired
    private VacanciesDAO vacanciesDAO;
/*
    public static void main(String[] args) {
        VacanciesServiceImpl vacanciesService = new VacanciesServiceImpl();
        vacanciesService.LoadDataBase();
    }*/

    @Transactional
    public void LoadDataBase() {

        Map<String, String> DataMap = Parser.getVacancyMap();
        Map<String, String> hashmap = new HashMap<String, String>();
        String query = "";
        for (String s : DataMap.keySet()) {
            hashmap = Parser.getDataForDBbyOneURL(s);
            for (Map.Entry<String, String> pair : hashmap.entrySet()) {
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }

            query = "INSERT INTO Vacancies (vacancy, salary, experience, education, employer, details, hr, url)" +
                    " VALUES (" + "\'" + hashmap.get("vacancy") + "\'" + ", " + "\'" + hashmap.get("salary") + "\'" + ", " + "\'" + hashmap.get("experience") + "\'" + ", " + "\'" + hashmap.get("education") + "\'" + ", " +
                    "\'" + hashmap.get("employer") + "\'" + ", " + "\'" + hashmap.get("details") + "\'" + ", " + "\'" + hashmap.get("hr") + "\'"+ ", " + "\'" + hashmap.get("url") + "\'" + ")";

            vacanciesDAO.LoadDataBase(query);
        }

    }

    @Transactional
    public void FindVacancy(String queries) {

        StringBuilder result = new StringBuilder();
        ArrayList<String> arrayListQueries = new ArrayList<String>();
        StringBuilder query = new StringBuilder();
        String sqlQuery = "";

        String[] arrayQueriesTmp = queries.replaceAll("\\s+", " ").trim().split(" ");

        for (int i = 0; i < arrayQueriesTmp.length; i++) {
            if (arrayQueriesTmp[i].matches("^[0-9a-zA-Zа-яА-Я]*$")) {
                StringBuilder request = new StringBuilder();
                request.append("%").append(arrayQueriesTmp[i]).append("%");
                arrayListQueries.add(request.toString());
            }
        }
        result.append("По вашему запросу \"");
        for (int i = 0; i < arrayQueriesTmp.length; i++) {
            result.append(arrayQueriesTmp[i]);
            if (i < arrayQueriesTmp.length - 1) {
                result.append(" ");
            }
        }
        result.append("\" ничего не найдено");

        if (arrayListQueries.size() != 0) {
            query.append("SELECT * FROM Vacancies WHERE ");
            query.append("vacancy LIKE \'");
            for (int i = 0; i < arrayListQueries.size(); i++) {
                query.append(arrayListQueries.get(i));
                if (i < arrayListQueries.size() - 1) {
                    query.append(" ");
                }
                if (i == arrayListQueries.size() - 1) {
                    query.append("\'");
                }
            }
            query.append(" OR details LIKE \'");
            for (int i = 0; i < arrayListQueries.size(); i++) {
                query.append(arrayListQueries.get(i));
                if (i < arrayListQueries.size() - 1) {
                    query.append(" ");
                }
                if (i == arrayListQueries.size() - 1) {
                    query.append("\'");
                }
            }
            sqlQuery = query.toString();
        } else {
            sqlQuery = "";
        }
        vacanciesDAO.FindVacancy(queries);
    }
}
