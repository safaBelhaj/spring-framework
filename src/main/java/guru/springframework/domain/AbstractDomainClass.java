package guru.springframework.domain;

import javax.persistence.*;
import java.util.Date;

/* it tells jpa that it can inherit propreties from it */
@MappedSuperclass
public class AbstractDomainClass implements DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    private Date dateCreated;
    private Date lastUpdated;

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }


    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUploaded() {
        return lastUpdated;
    }
    public void setDateUploaded(Date dateUploaded) {
        this.lastUpdated = lastUpdated;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps(){
        lastUpdated=new Date();
        if(dateCreated == null){
            dateCreated = new Date();
        }
    }
}
