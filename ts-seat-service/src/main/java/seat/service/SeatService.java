package seat.service;

import org.springframework.http.HttpHeaders;
import seat.entity.SeatRequest;
import seat.entity.Ticket;

public interface SeatService {

    Ticket distributeSeat(SeatRequest seatRequest,HttpHeaders headers);
    int getLeftTicketOfInterval(SeatRequest seatRequest,HttpHeaders headers);
}
