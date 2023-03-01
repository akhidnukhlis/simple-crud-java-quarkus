package akhid.development.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(schema = "public", name = "cake")
public class Cake extends PanacheEntityBase {
    @Id
    @Column(name = "id", length = 255)
    public String id;

    @Column(name = "tittle", length = 100)
    public String tittle;

    @Column(name = "description", length = 255)
    public String description;

    @Column(name = "rating", length = 5)
    public int rating;

    @Column(name = "image", length = 255)
    public String image;

    @Column(name = "activated")
    public boolean activated;

    @Column(name = "created_at")
    public Timestamp createdAt;

    @Column(name = "updated_at")
    public Timestamp updatedAt;

    @Column(name = "deleted_at")
    public Timestamp deletedAt;
}
