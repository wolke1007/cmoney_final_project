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

import javax.print.Doc;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
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
    private DoctorRepository doctorRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminBookingReservation(
            HttpServletResponse response,
            @RequestBody DtoReservation request) {
        try {
            Schedule schedule = scheduleRepository.findByDayAndTime(request.getDay(), request.getTime()).orElse(null);
            if (schedule == null) {
                return new CommonResponse("schedule not found.", 404).toString();
            }
            int scheduleId = schedule.getId();
            Roaster roaster = roasterRepository.findByDoctorIdAndScheduleId(request.getDoctorId(), scheduleId).orElse(null);
            if (roaster == null) {
                return new CommonResponse("roaster not found.", 404).toString();
            }
            int roasterId = roaster.getId();
            User user = userRepository.findById(request.getUserId()).orElse(null);
            if (user == null) {
                return new CommonResponse("user not found.", 404).toString();
            }
            Pet pet = petRepository.findById(request.getPetId()).orElse(null);
            if (pet == null) {
                return new CommonResponse("pet not found.", 404).toString();
            }
            if (user.getId() != pet.getUserId()) {
                return new CommonResponse("pet's owner is not userId:" + user.getId(), 404).toString();
            }
            Reservation reservation = reservationRepository.findByUserIdAndPetIdAndRoasterIdAndDate(
                    request.getUserId(),
                    request.getPetId(),
                    roasterId,
                    request.getDate()
            ).orElse(null);
            if (reservation != null) { // 同飼主 同寵物 同醫院 同天 同時段 的預約已經存在，不準預約
                return new CommonResponse("booked before, booking number is:" + reservation.getNumber(), 404).toString();
            }
            Reservation newRes = new Reservation();
            newRes.setUserId(user.getId());
            // 這邊考慮改成用 username 來做，前端會比較好傳值進來
            int reservePatientCnt = reservationRepository.findAllByRoasterIdAndDate(roasterId, request.getDate()).size();
            int bookingNum = reservePatientCnt + 1; // 預約這個班表且為同天的人數
            newRes.setNumber(bookingNum);
            newRes.setDate(request.getDate());
            newRes.setRoasterId(roasterId);
            newRes.setPetId(request.getPetId());
            reservationRepository.save(newRes);
            return new CommonResponse("reservation_id: " + bookingNum, 200).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        } catch (NoSuchElementException e) {
            return new CommonResponse("booking fail because wrong value is given.", 404).toString();
        }
    }

    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminEditReservation(@RequestBody Reservation request) {
        Reservation reservation = reservationRepository.findById(request.getId()).get();
        reservation.setUserId(request.getUserId());
        reservation.setPetId(request.getPetId());
        reservation.setRoasterId(request.getRoasterId());
        reservation.setDate(request.getDate());
        reservationRepository.save(reservation);
        return new CommonResponse("success", 200).toString();
    }

    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminDeleteReservation(
            HttpServletResponse response,
            @RequestBody Reservation request
    ) {
        try {
            Reservation res = reservationRepository.findById(request.getId()).orElse(null); // 確認 id 是否可以找到東西
            if (res == null) {
                response.setStatus(404);
                return new CommonResponse("reservation " + request.getId() + " not found", 404).toString();
            }
            reservationRepository.delete(res);
            return new CommonResponse("success", 200).toString();
        } catch (DataIntegrityViolationException e) {
            response.setStatus(404);
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
        } catch (NoSuchElementException e) {
            response.setStatus(404);
            return new CommonResponse("id " + request.getId() + " not found: " + e.getMessage(), 404).toString();
        }
    }

    // 有效能問題需要改善
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String adminGetAllReservationsByHostpitalId(HttpServletResponse response,
                                                       @RequestParam(value = "hospitalId")
                                                               int hospitalId) {
        try {
            List<Reservation> reservations = reservationRepository.findReservationByHospitalId(hospitalId);
            JsonIter ji = new JsonIter();
            JsonArray arr = ji.listIntoArrayWithoutKey(reservations, "roasterId");
            Gson g = new Gson();
            for (Reservation res : reservations) {
                int roaId = res.getRoasterId();
                Roaster roaster = roasterRepository.findById(roaId).get();
                int doctorId = roaster.getDoctorId();
                Doctor doctor = doctorRepository.findById(doctorId).get();
                String doctorName = userRepository.findById(doctor.getUserId()).get().getName();
                User user = userRepository.findById(res.getUserId()).get();
                String userPhone = user.getPhone();
                String userName = user.getName();
                Pet pet = petRepository.findById(res.getPetId()).get();
                int scheduleId = roaster.getScheduleId();
                Schedule schedule = scheduleRepository.findById(scheduleId).get();
                String time = schedule.getDay() + " " + schedule.getTime();
                System.out.println("userid:" + user.getId() + " petid:" + pet.getId());
                MedicalRecord medicalRecord = medicalRecordRepository.findByUserIdAndPetId(user.getId(), pet.getId()).orElse(null);
                for (JsonElement je : arr) {
                    je.getAsJsonObject().addProperty("doctorName", doctorName);
                    je.getAsJsonObject().addProperty("userName", userName);
                    je.getAsJsonObject().addProperty("userPhone", userPhone);
                    je.getAsJsonObject().addProperty("petName", pet.getName());
                    je.getAsJsonObject().addProperty("petAge", pet.getAge());
                    je.getAsJsonObject().addProperty("petSpecies", pet.getSpecies());
                    je.getAsJsonObject().addProperty("petBreed", pet.getBreed());
                    je.getAsJsonObject().addProperty("petOwnDate", pet.getOwnDate());
                    je.getAsJsonObject().addProperty("day", schedule.getDay());
                    je.getAsJsonObject().addProperty("time", schedule.getTime());
                    JsonArray medicalTreatments = medicalRecord == null ? null : g.toJsonTree(medicalRecord).getAsJsonObject().get("medicalTreatments").getAsJsonArray();
                    JsonIter jii = new JsonIter();
                    JsonArray descriptions = jii.listIntoArrayWithKeys(medicalTreatments, Arrays.asList("description"));
                    if(descriptions.size() == 0){
                        JsonObject jsonDes = new JsonObject();
                        jsonDes.addProperty("description", "沒有診療紀錄");
                        descriptions.add(jsonDes);
                    }
                    je.getAsJsonObject().add("medicalTreatments", descriptions);
                }
                System.out.println("DEBUG8");
            }
            System.out.println("DEBUG9");
            return new CommonResponse(arr, 200).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        }
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String adminGetReservationByDoctorId(
            HttpServletResponse response,
            @RequestParam int doctorId) {
        try {
            List<Reservation> reservations = reservationRepository.findReservationByDoctorId(doctorId);
            JsonIter ji = new JsonIter();
            JsonArray arr = ji.listIntoArrayWithoutKey(reservations, "roasterId");
            Gson g = new Gson();
            for (Reservation res : reservations) {
                int roaId = res.getRoasterId();
                Roaster roaster = roasterRepository.findById(roaId).get();
                Doctor doctor = doctorRepository.findById(doctorId).get();
                String doctorName = userRepository.findById(doctor.getUserId()).get().getName();
                User user = userRepository.findById(res.getUserId()).get();
                String userPhone = user.getPhone();
                String userName = user.getName();
                Pet pet = petRepository.findById(res.getPetId()).get();
                int scheduleId = roaster.getScheduleId();
                Schedule schedule = scheduleRepository.findById(scheduleId).get();
                String time = schedule.getDay() + " " + schedule.getTime();
                System.out.println("userid:" + user.getId() + " petid:" + pet.getId());
                MedicalRecord medicalRecord = medicalRecordRepository.findByUserIdAndPetId(user.getId(), pet.getId()).orElse(null);
                for (JsonElement je : arr) {
                    je.getAsJsonObject().addProperty("doctorName", doctorName);
                    je.getAsJsonObject().addProperty("userName", userName);
                    je.getAsJsonObject().addProperty("userPhone", userPhone);
                    je.getAsJsonObject().addProperty("petName", pet.getName());
                    je.getAsJsonObject().addProperty("petAge", pet.getAge());
                    je.getAsJsonObject().addProperty("petSpecies", pet.getSpecies());
                    je.getAsJsonObject().addProperty("petBreed", pet.getBreed());
                    je.getAsJsonObject().addProperty("petOwnDate", pet.getOwnDate());
                    je.getAsJsonObject().addProperty("day", schedule.getDay());
                    je.getAsJsonObject().addProperty("time", schedule.getTime());
                    JsonArray medicalTreatments = medicalRecord == null ? null : g.toJsonTree(medicalRecord).getAsJsonObject().get("medicalTreatments").getAsJsonArray();
                    JsonIter jii = new JsonIter();
                    JsonArray descriptions = jii.listIntoArrayWithKeys(medicalTreatments, Arrays.asList("description"));
                    if(descriptions.size() == 0){
                        JsonObject jsonDes = new JsonObject();
                        jsonDes.addProperty("description", "沒有診療紀錄");
                        descriptions.add(jsonDes);
                    }
                    je.getAsJsonObject().add("medicalTreatments", descriptions);
                }
                System.out.println("DEBUG8");
            }
            System.out.println("DEBUG9");
            return new CommonResponse(arr, 200).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        }
    }
}
