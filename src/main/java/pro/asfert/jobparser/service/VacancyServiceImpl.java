package pro.asfert.jobparser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.asfert.jobparser.dao.VacancyDAO;
import pro.asfert.jobparser.domain.Parser;
import pro.asfert.jobparser.domain.Vacancy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VacancyServiceImpl implements VacancyService {


    @Autowired
    private VacancyDAO VacancyDAO;

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

            VacancyDAO.LoadDataBase();
        }

    }

    @Transactional
    public List <String> FindVacancy(String queries) {

        StringBuilder result = new StringBuilder();
        ArrayList<String> arrayListQueries = new ArrayList<String>();
        StringBuilder query = new StringBuilder();
        String sqlQuery = "";

        String[] arrayQueriesTmp = queries.replaceAll("\\s+", " ").trim().split(" ");

        for (int i = 0; i < arrayQueriesTmp.length; i++) {
            if (!arrayQueriesTmp[i].matches("^[0-9a-zA-Zа-яА-Я]*$")) {
                result.append("По вашему запросу \"");
                for (int k = 0; i < arrayQueriesTmp.length; k++) {
                    result.append(arrayQueriesTmp[k]);
                    if (i < arrayQueriesTmp.length - 1) {
                        result.append(" ");
                    }
                }
                result.append("\" ничего не найдено");
                break;
            }
                StringBuilder request = new StringBuilder();
                request.append("%").append(arrayQueriesTmp[i]).append("%");
                arrayListQueries.add(request.toString());

        }

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
            sqlQuery = result.toString();
        }
        return VacancyDAO.FindVacancy(sqlQuery);
    }

    @Transactional
    public void addVacancy(Vacancy vacancy) {
        VacancyDAO.addVacancy(vacancy);
    }

    @Transactional
    public List<Vacancy> listVacancy() {
        return VacancyDAO.listVacancy();
    }

    @Transactional
    public void removeVacancy(Integer id) {
        VacancyDAO.removeVacancy(id);
    }
}
