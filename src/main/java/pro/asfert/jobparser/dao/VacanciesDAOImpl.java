package pro.asfert.jobparser.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VacanciesDAOImpl implements VacanciesDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void LoadDataBase(String query) {
        sessionFactory.getCurrentSession().createQuery(query);
    }

    public void FindVacancy(String queries) {
        if (queries.contains("По вашему запросу")) {
            System.out.println(queries);
        } else {
            sessionFactory.getCurrentSession().createQuery(queries);
        }
    }
}
