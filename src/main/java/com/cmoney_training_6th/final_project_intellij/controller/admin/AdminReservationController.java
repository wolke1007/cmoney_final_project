package com.cmoney_training_6th.final_project_intellij.controller.admin;

import com.cmoney_training_6th.final_project_intellij.model.*;
import com.cmoney_training_6th.final_project_intellij.model.dto.DtoReservation;
import com.cmoney_training_6th.final_project_intellij.repos.*;
import com.cmoney_training_6th.final_project_intellij.services.DoctorService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.google.gson.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/admin/reservation")
public class AdminReservationController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private RoasterRepository roasterRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminBookingReservation(
            HttpServletResponse response,
            @RequestBody DtoReservation request) {
        try {
            Optional<User> user = userRepository.findById(request.getUserId());
            List<Reservation> reservations = reservationRepository.
                    findAllByRoasterIdAndDateAndUserId(request.getRoasterId(), request.getDate(), user.get().getId());
            if (reservations.size() >= 1) {
                int bookingNum = reservations.get(reservations.size() - 1).getNumber();
                return new CommonResponse("booked before, booking number is:" + bookingNum, 404).toString();
            }
            Reservation newRes = new Reservation();
            newRes.setUserId(user.get().getId());
            // 這邊考慮改成用 username 來做，前端會比較好傳值進來
            System.out.println("DEBUG user_id: " + user.get().getId());
            int reservePatientCnt = reservationRepository.findAllByRoasterIdAndDate(request.getRoasterId(), request.getDate()).size();
            System.out.println("DEBUG reservePatientCnt: " + reservePatientCnt);
            int bookingNum = reservePatientCnt + 1; // 預約這個班表且為同天的人數
            newRes.setNumber(bookingNum);
            newRes.setDate(request.getDate());
            newRes.setRoasterId(request.getRoasterId());
            reservationRepository.save(newRes);
            return new CommonResponse("reservation_id: " + bookingNum, 200).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        } catch (NoSuchElementException e) {
            return new CommonResponse("booking fail because wrong value is given.", 404).toString();
        }
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminGetAllReservationsByHostpitalId(@RequestParam(value = "hospitalId")
                                                       int hospitalId) {
        List<Reservation> reservations = reservationRepository.findReservationByHospitalId(hospitalId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKey(reservations, "roasterId");
        for(Reservation res : reservations){
            int roaId = res.getRoasterId();
            Roaster roaster = roasterRepository.findById(roaId).get();
            int doctorId = roaster.getDoctorId();
            int scheduleId = roaster.getScheduleId();
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
            String time = schedule.getDay() + " " + schedule.getTime();
            for(JsonElement je : arr){
                Optional<User> u = userRepository.findById(je.getAsJsonObject().get("userId").getAsInt());
                je.getAsJsonObject().addProperty("doctorId", doctorId);
                je.getAsJsonObject().addProperty("userName", u.get().getUsername());
                je.getAsJsonObject().addProperty("day", schedule.getDay());
                je.getAsJsonObject().addProperty("time", schedule.getTime());
            }
        }
        return new CommonResponse(arr, 200).toString();
    }

    @PutMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminEditReservation(@RequestBody Reservation request) {
        Reservation reservation = reservationRepository.findById(request.getId()).get();
        request.setId(reservation.getId());
        reservationRepository.save(request);
        return new CommonResponse("success", 200).toString();
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminDeleteReservation(
            HttpServletResponse response,
            @RequestBody Reservation request
    ) {
        try {
            reservationRepository.findById(request.getId()).get(); // 確認 id 是否可以找到東西，沒找到會噴掉被 catch
            reservationRepository.delete(request);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("id " + request.getId() + " not found: " + e.getMessage(), 404).toString();
        }
    }

}
