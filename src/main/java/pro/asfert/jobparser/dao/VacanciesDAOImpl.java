package pro.asfert.jobparser.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pro.asfert.jobparser.service.VacanciesServiceImpl;

@Repository
public class VacanciesDAOImpl implements VacanciesDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void LoadDataBase() {
        sessionFactory.getCurrentSession().createQuery(new VacanciesServiceImpl().LoadDataBase());
    }

    @Override
    public void FindVacancy(String query) {
        sessionFactory.getCurrentSession().createQuery(query);
    }
}
