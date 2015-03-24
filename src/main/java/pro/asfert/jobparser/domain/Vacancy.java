package pro.asfert.jobparser.domain;
import javax.persistence.*;


@Entity
@Table(name = "Vacancies")
public class Vacancy {
    public Vacancy() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column (name = "vacancy")
    private String vacancy;

    @Column (name = "salary")
    private String salary;

    @Column (name = "experience")
    private String experience;

    @Column (name = "education")
    private String education;

    @Column (name = "employer")
    private String employer;

    @Column (name = "details")
    private String details;

    @Column (name = "hr")
    private String hr;

    @Column (name = "url")
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}