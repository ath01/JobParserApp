package pro.asfert.jobparser.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pro.asfert.jobparser.domain.Vacancy;
import java.util.List;


@Repository
public class VacancyDAOImpl implements VacancyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void LoadDataBase() {

    }

    @SuppressWarnings("unchecked")
    public List <String> FindVacancy(String queries) {
        if (queries.contains("По вашему запросу: ")) {
            System.out.print(queries);
            return null;
        } else {
            return sessionFactory.getCurrentSession().createSQLQuery(queries).list();
        }
    }

    public void addVacancy(Vacancy vacancy) {
        sessionFactory.getCurrentSession().save(vacancy);
    }

    @SuppressWarnings("unchecked")
    public List<Vacancy> listVacancy() {
        return sessionFactory.getCurrentSession().createQuery("from Vacancy").list();
    }


    public void removeVacancy(Integer id) {
        Vacancy vacancy = (Vacancy) sessionFactory.getCurrentSession().load(
                Vacancy.class, id);
        if (vacancy != null) {
            sessionFactory.getCurrentSession().delete(vacancy);
        }
    }


}
