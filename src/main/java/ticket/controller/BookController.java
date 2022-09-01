package ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ticket.dto.BookDto;
import ticket.dto.BookHistDto;
import ticket.dto.UserHistDto;
import ticket.service.BookService;
import ticket.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @PostMapping(value = "/book")
    public @ResponseBody ResponseEntity book(@RequestBody @Valid BookDto bookDto, BindingResult bindingResult,
                                             Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String userId = principal.getName();
        Long bookId;
        try {
            bookId = bookService.book(bookDto, userId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(bookId, HttpStatus.OK);
    }

    @GetMapping(value = {"/books", "/books/{page}"})
    public String bookHist(@PathVariable("page")Optional<Integer> page, Principal principal, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        UserHistDto userHistDto = userService.getUserHist(principal.getName());

        Page<BookHistDto> bookHistDtoList = bookService.getBookList(principal.getName(), pageable);

        model.addAttribute("user", userHistDto);
        model.addAttribute("books", bookHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "book/bookHist";
    }
}
