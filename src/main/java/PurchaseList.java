import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Purchaselist")
@Getter
@Setter
public class PurchaseList implements Serializable {
    @EmbeddedId
    private PKey id;

    @Column(name = "student_name", insertable = false, updatable = false, nullable = false)
    private String studentName;

    @Column(name = "course_name",  insertable = false, updatable = false, nullable = false)
    private String courseName;

    private int price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false, nullable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name ="course_id", insertable = false, updatable = false, nullable = false)
    private Course course;

}
