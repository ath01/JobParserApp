package pro.asfert.jobparser.domain;

import javax.persistence.*;

/**
 * Created by darkwawe on 06.03.2015.
 */
@Entity
@Table(name = "vacations")
public class Vacations {
    public Vacations(String category) {

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



}