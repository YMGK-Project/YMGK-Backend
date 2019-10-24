package proje.v1.api.base.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@MappedSuperclass
@Getter
@Setter
public class BaseModel {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted = false;

    @PrePersist
    public void onCreate(){
        createdAt = new Date();
    }

    @PostPersist
    public void onUpdate(){
        updatedAt = new Date();
    }
}
