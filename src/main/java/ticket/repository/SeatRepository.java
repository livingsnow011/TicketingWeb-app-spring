package ticket.repository;

import org.springframework.stereotype.Repository;
import ticket.entity.Seat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SeatRepository{
    @PersistenceContext
    private EntityManager em;

    public void save(Seat seat){
        em.persist(seat);
    }

    public Seat findOne(Long id){
        return em.find(Seat.class,id);
    }

    public List<Seat> findAll(){
        List<Seat> result = em.createQuery("select s from Seat s",Seat.class).getResultList();
        return result;
    }

}
