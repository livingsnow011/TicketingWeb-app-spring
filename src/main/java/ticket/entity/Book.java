package ticket.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ticket.constant.BookStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "book")
@Entity
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    private LocalDateTime bookDate;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    //보통 1:N 관계에서 고아객체제거를 활성화 해주는 것이 좋다.
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<BookShow> bookShows = new ArrayList<>();

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    //test 용 지워
    public void setUser(User user) {
        this.user = user;
    }
}
