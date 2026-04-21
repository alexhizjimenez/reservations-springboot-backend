package com.alexhiz.reservations.service;

import java.util.List;
import java.util.UUID;

import com.alexhiz.reservations.model.ConsultProdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alexhiz.reservations.dto.ReservationDTO;
import com.alexhiz.reservations.model.Reservation;

public interface IReservationService extends ICRUD<Reservation, UUID> {
    Reservation saveReservationTransactional(ReservationDTO dto);

    Page<Reservation> listPage(Pageable pageable);

    List<ConsultProdDTO> callProcedureOrFunctionManual();

    byte[] generateReport() throws Exception;

}
