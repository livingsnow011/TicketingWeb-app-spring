package ticket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticket.constant.Classification;
import ticket.dto.ShowGetDto;
import ticket.entity.Show;
import ticket.entity.ShowImg;

import java.util.List;

public interface ShowImgRepository extends JpaRepository<ShowImg,Long> {

    List<ShowImg> findByShowId(Long showId);

    List<ShowImg> findByShowIdOrderByIdAsc(Long showId);

    @Modifying
    @Query("delete from ShowImg si where si.show=:show")
    void deleteAllByShowIdInQuery(@Param("show") Show show);

    @Query("select new ticket.dto.ShowGetDto(s.id,s.showName,s.classification,s.showSellStatus,i.imgUrl) " +
            "from ShowImg i join i.show s " +
            "where s.classification=:classification and i.repImgYn='Y' order by s.id desc")
    Page<ShowGetDto> findByClassification(@Param("classification") Classification Classification, Pageable pageable);

    @Query("select new ticket.dto.ShowGetDto(s.id,s.showName,s.classification,s.showSellStatus,i.imgUrl) " +
            "from ShowImg i join i.show s " +
            "where i.repImgYn='Y' order by s.id desc")
    Page<ShowGetDto> findAllShow(Pageable pageable);

    ShowImg findByShowIdAndRepImgYn(Long showId, String repImgYn);
}
