package com.alexhiz.reservations.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.alexhiz.reservations.model.ConsultProdDTO;
import org.springframework.data.jpa.repository.Query;

import com.alexhiz.reservations.model.Reservation;

public interface IReservationRepo extends IGenericRepo<Reservation, UUID> {
    @Query("""
                SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
                FROM Reservation r
                WHERE r.room.id = :roomId
                AND (
                    (:checkIn BETWEEN r.checkInDate AND r.checkOutDate)
                    OR
                    (:checkOut BETWEEN r.checkInDate AND r.checkOutDate)
                    OR
                    (r.checkInDate BETWEEN :checkIn AND :checkOut)
                )
            """)
    boolean existsByRoomAndDateRange(
            UUID roomId,
            LocalDate checkIn,
            LocalDate checkOut);

    @Query(value = "select * from fn_list_reservations()", nativeQuery = true)
    List<ConsultProdDTO> callProcedureOrFunctionManual();
}
