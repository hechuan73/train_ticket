package seat.service;

import seat.domain.SeatRequest;
import seat.domain.Ticket;

public interface SeatService {

    Ticket distributeSeat(SeatRequest seatRequest);
    int getLeftTicketOfInterval(SeatRequest seatRequest);
}
