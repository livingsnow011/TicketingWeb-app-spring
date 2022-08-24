package ticket.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ticket.constant.Classification;
import ticket.repository.BookRepository;
import ticket.repository.BookShowRepository;
import ticket.repository.ShowRepository;
import ticket.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SpringBootTest
class BookTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ShowRepository showRepository;

    @PersistenceContext
    EntityManager em;

    public Show createShow(){
        Show show = Show.builder()
                .showName("테스트 공연")
                .classification(Classification.MOVIE)
                .showDetail("테스트 공연 상세")
                .build();

        return show;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        Book book = new Book();

        for (int i = 0; i < 3; i++) {
            Show show = createShow();
            showRepository.save(show);
            BookShow bookShow = BookShow.builder().book(book).show(show).build();
            book.getBookShows().add(bookShow);
        }

        bookRepository.saveAndFlush(book);
        em.clear();

        Book bookedShow = bookRepository.findById(book.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3,bookedShow.getBookShows().size());
    }


}