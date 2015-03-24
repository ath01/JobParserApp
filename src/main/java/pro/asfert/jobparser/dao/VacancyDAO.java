package pro.asfert.jobparser.dao;

import pro.asfert.jobparser.domain.Vacancy;

import java.util.List;

public interface VacancyDAO {
    void LoadDataBase();

    List <String> FindVacancy(String queries);

    void addVacancy(Vacancy vacancy);

    List<Vacancy> listVacancy();

    void removeVacancy(Integer id);
}
