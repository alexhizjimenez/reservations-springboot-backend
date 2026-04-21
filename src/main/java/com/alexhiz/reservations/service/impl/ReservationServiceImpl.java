package com.alexhiz.reservations.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alexhiz.reservations.exception.ModelNotFoundException;
import com.alexhiz.reservations.model.ConsultProdDTO;
import org.springframework.core.io.ClassPathResource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alexhiz.reservations.dto.ReservationDTO;
import com.alexhiz.reservations.model.Customer;
import com.alexhiz.reservations.model.Reservation;
import com.alexhiz.reservations.model.Room;
import com.alexhiz.reservations.repo.ICustomerRepo;
import com.alexhiz.reservations.repo.IGenericRepo;
import com.alexhiz.reservations.repo.IReservationRepo;
import com.alexhiz.reservations.repo.IRoomRepo;
import com.alexhiz.reservations.service.IReservationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl extends CRUDImpl<Reservation, UUID> implements IReservationService {

    private final IReservationRepo repo;
    private final ICustomerRepo customerRepo;
    private final IRoomRepo roomRepo;

    @Override
    protected IGenericRepo<Reservation, UUID> getRepo() {
        return repo;
    }

    @Transactional
    @Override
    public Reservation saveReservationTransactional(ReservationDTO dto) {

        if (dto.getCheckInDate().isAfter(dto.getCheckOutDate())) {
            throw new ModelNotFoundException("La fecha de salida no puede ser menor a la de entrada");
        }

        Room room = roomRepo.findById(dto.getRoom().getId())
                .orElseThrow(() -> new ModelNotFoundException("Room no existe"));

        if (!room.getAvailable()) {
            throw new ModelNotFoundException("La habitación no está disponible");
        }

        boolean existsConflict = repo.existsByRoomAndDateRange(
                dto.getRoom().getId(),
                dto.getCheckInDate(),
                dto.getCheckOutDate());

        if (existsConflict) {
            throw new ModelNotFoundException("La habitación ya está reservada en esas fechas");
        }

        Customer customer = customerRepo.findById(dto.getCustomer().getId())
                .orElseThrow(() -> new ModelNotFoundException("Customer no existe"));

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCustomer(customer);
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        room.setAvailable(false);

        return repo.save(reservation);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        Reservation reservation = repo.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("ID NO ENCONTRADO: " + id));

        Room room = reservation.getRoom();
        room.setAvailable(true);
        roomRepo.save(room);
        repo.deleteById(id);
    }

    @Override
    public Page<Reservation> listPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<ConsultProdDTO> callProcedureOrFunctionManual() {
        return repo.callProcedureOrFunctionManual();
    }


    @Override
    public byte[] generateReport() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("txt_title", "RESERVATION CONSULT REPORT");

        File file = new ClassPathResource("/reports/reservations.jasper").getFile();
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, new JRBeanCollectionDataSource(callProcedureOrFunctionManual()));
        return JasperExportManager.exportReportToPdf(print);
    }
}
