package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.constant.BookStatus;
import ticket.dto.BookDto;
import ticket.dto.BookHistDto;
import ticket.dto.TicketDto;
import ticket.entity.*;
import ticket.exception.BookOfSameShowException;
import ticket.exception.OverOfShowDateException;
import ticket.repository.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowDateRepository showDateRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final BookRepository bookRepository;
    private final ShowImgRepository showImgRepository;

    public Long book(BookDto bookDto, String userId){
        ShowSeat showSeat = showSeatRepository.findById(bookDto.getSeatId()).
                orElseThrow(EntityNotFoundException::new);
        ShowDate showDate = showDateRepository.findById(bookDto.getDateId()).
                orElseThrow(EntityNotFoundException::new);

        Long showId = showDateRepository.findShowIdByShowDateId(bookDto.getDateId());

        if(showDate.getIsBooked()){
            throw new OverOfShowDateException("이미 예매가 종료된 공연입니다");
        }

        User user = userRepository.findByUserId(userId);
        user.usePoint(showSeat.getPrice());

        if(bookRepository.existsByShowIdAndUser(showId,user)){
            throw new BookOfSameShowException("이미 예매한 공연입니다.");
        }

        Ticket ticket = Ticket.builder().showSeat(showSeat).showDate(showDate).build();

        Book book = Book.builder().
                user(user).
                bookDate(LocalDateTime.now()).
                bookStatus(BookStatus.BOOKING).
                ticket(ticket).
                showId( showId).
                build();

        ticket.setBook(book);

        bookRepository.save(book);

        return book.getId();
    }

    @Transactional(readOnly = true)
    public Page<BookHistDto> getBookList(String userId, Pageable pageable){

        List<Book> bookList = bookRepository.findBooks(userId, pageable);
        Long totalCount = bookRepository.countBook(userId);

        List<BookHistDto> bookHistDtoList = new ArrayList<>();

        for(Book book : bookList){
            Ticket ticket = book.getTicket();
            ShowImg showImg = showImgRepository.findByShowIdAndRepImgYn(ticketRepository.findShowIdByTicketId(ticket.getId()),"Y");
            TicketDto ticketDto = new TicketDto(ticket, showImg.getImgUrl());
            BookHistDto bookHistDto = new BookHistDto(book,ticketDto);

            bookHistDtoList.add(bookHistDto);
        }

        return new PageImpl<BookHistDto>(bookHistDtoList, pageable, totalCount);
    }

}
