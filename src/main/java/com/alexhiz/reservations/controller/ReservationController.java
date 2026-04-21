package com.alexhiz.reservations.controller;

import com.alexhiz.reservations.dto.ReservationDTO;
import com.alexhiz.reservations.model.ConsultProdDTO;
import com.alexhiz.reservations.model.Reservation;
import com.alexhiz.reservations.service.IReservationService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReservationController {

    private final IReservationService reservationService;

    @Qualifier("reservationMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() throws Exception {
        List<ReservationDTO> reservations = reservationService.findAll().stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Reservation>> listPage(Pageable pageable) throws Exception {
        Page<Reservation> page = reservationService.listPage(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable UUID id) throws Exception {
        ReservationDTO reservation = convertToDto(reservationService.findById(id));
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO dto) throws Exception {
        Reservation savedReservation = reservationService.saveReservationTransactional(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReservation.getIdReservation())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable UUID id, @RequestBody ReservationDTO dto)
            throws Exception {
        Reservation updateReservation = reservationService.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDto(updateReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) throws Exception {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Reservation convertToEntity(ReservationDTO dto) {
        return modelMapper.map(dto, Reservation.class);
    }

    private ReservationDTO convertToDto(Reservation obj) {
        return modelMapper.map(obj, ReservationDTO.class);
    }

    @GetMapping("/callProcedureManual")
    public ResponseEntity<List<ConsultProdDTO>> callProcedureManual(){
        return ResponseEntity.ok(reservationService.callProcedureOrFunctionManual());
    }

    @GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_PDF_VALUE) //MediaType.APPLICATION_PDF_VALUE , APPLICATION_OCTET_STREAM_VALUE
    public ResponseEntity<byte[]> generateReport() throws Exception {
        return ResponseEntity.ok(reservationService.generateReport());
    }

}
