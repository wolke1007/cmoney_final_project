package com.cmoney_training_6th.final_project_intellij.controller.user;

import com.cmoney_training_6th.final_project_intellij.model.*;
import com.cmoney_training_6th.final_project_intellij.model.dto.DtoReservation;
import com.cmoney_training_6th.final_project_intellij.dao.*;
import com.cmoney_training_6th.final_project_intellij.service.DoctorService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JsonIter;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.google.gson.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/hospital")
public class HospitalController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private RoasterRepository roasterRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getAllHospitals() {
        // TODO should refactor here, because fat controller design
        Gson g = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create();
        List<Hospital> hospitals = hospitalRepository.findAll();
        JsonIter ji = new JsonIter();
        JsonArray jsonArr = new JsonArray();
        jsonArr = ji.listIntoArrayWithKeys(hospitals,
                Arrays.asList("id", "name", "phone"));
        return new CommonResponse(jsonArr, 200).toString();
    }

    @GetMapping(path = "/doctors", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getDoctorDetailByHostpitalId(@RequestParam(value = "hospitalId")
                                                       int hospitalId) {
        Gson g = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create();
        List<Doctor> doctors = doctorService.getDoctorsByHospitalId(hospitalId);
        JsonIter ji = new JsonIter();
        return new CommonResponse(ji.listIntoArray(doctors), 200).toString();
    }

    @PostMapping(path = "/reservation/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public String bookingByDoctorId(
            HttpServletResponse response,
            @RequestBody DtoReservation request,
            @RequestHeader("Authorization") String header) {
        // TODO should refactor here, because fat controller design
        try {
            String token = header.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            User user = userRepository.findByUsername(username).orElse(null);
            int scheduleId = scheduleRepository.findByDayAndTime(request.getDay(), request.getTime()).get().getId();
            int roasterId = roasterRepository.findByDoctorIdAndScheduleId(request.getDoctorId(), scheduleId).get().getId();
            if (petRepository.findByUserIdAndId(user.getId(), request.getPetId()).orElse(null) == null) {
                response.setStatus(404);
                return new CommonResponse("pet:" + request.getPetId() +
                        " of this user:" + user.getId() + " can't be found", 404).toString();
            }
            List<Reservation> reservations = reservationRepository.
                    findAllByRoasterIdAndDateAndPetId(roasterId, request.getDate(), request.getPetId());
            if (reservations.size() >= 1) {
                int bookingNum = reservations.get(reservations.size() - 1).getNumber();
                return new CommonResponse("booked before, booking number is:" + bookingNum, 404).toString();
            }
            Reservation newRes = new Reservation();
            newRes.setUserId(user.getId());
            // 這邊考慮改成用 username 來做，前端會比較好傳值進來
            System.out.println("DEBUG user_id: " + user.getId());
            int reservePatientCnt = reservationRepository.findAllByRoasterIdAndDate(roasterId, request.getDate()).size();
            System.out.println("DEBUG reservePatientCnt: " + reservePatientCnt);
            int bookingNum = reservePatientCnt + 1; // 預約這個班表且為同天的人數
            newRes.setNumber(bookingNum);
            newRes.setDate(request.getDate());
            newRes.setRoasterId(roasterId);
            newRes.setPetId(request.getPetId());
            MedicalRecord medicalRecord = medicalRecordRepository.findByUserIdAndPetId(user.getId(), request.getPetId()).orElse(null);
            if (medicalRecord == null) {
                medicalRecord = new MedicalRecord();
                medicalRecord.setUserId(user.getId());
                medicalRecord.setPetId(request.getPetId());
                medicalRecord.setHospitalId(request.getHospitalId());
                Date date = new Date();
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                medicalRecord.setCreateDate(sdf.format(date));
                medicalRecordRepository.save(medicalRecord);
            }
            reservationRepository.save(newRes);
            return new CommonResponse("reservation_id: " + bookingNum, 200).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        } catch (NoSuchElementException e) {
            return new CommonResponse("booking fail because wrong value is given.", 404).toString();
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
        } catch (StringIndexOutOfBoundsException e) {
            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
        }
//        try {
//            String token = header.substring(7);
//            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
//            Optional<User> user = userRepository.findByUsername(username);
//            List<Reservation> reservation = reservationRepository.
//                    findAllByRoasterIdAndDateAndUserId(request.getRoasterId(), request.getDate(), user.get().getId());
//            if (reservation.size() >= 1) {
//                int bookingNum = reservation.get(reservation.size() - 1).getNumber();
//                return new CommonResponse("booked before, booking number is:" + bookingNum, 404).toString();
//            }
//            request.setUserId(user.get().getId());
//            System.out.println("DEBUG user_id: " + user.get().getId());
//            int reservePatientCnt = reservationRepository.findAllByRoasterIdAndDate(request.getRoasterId(), request.getDate()).size();
//            System.out.println("DEBUG reservePatientCnt: " + reservePatientCnt);
//            int bookingNum = reservePatientCnt + 1; // 預約這個班表且為同天的人數
//            request.setNumber(bookingNum);
//            reservationRepository.save(request);
//            return new CommonResponse("reservation_id: " + bookingNum, 200).toString();
//        } catch (NoSuchElementException e) {
//            return new CommonResponse("booking fail because wrong value is given.", 404).toString();
//        } catch (ExpiredJwtException e) {
//            response.setStatus(403);
//            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
//        }
    }

    @GetMapping(path = "/roasters", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String userGetRoastersByHostpitalId(@RequestParam(value = "hospitalId")
                                                       int hospitalId) {
        // TODO should refactor here, because fat controller design
        List<Roaster> roasters = roasterRepository.findByHospitalId(hospitalId);
        JsonIter ji = new JsonIter();
        JsonArray arr = ji.listIntoArrayWithoutKeys(roasters,
                Arrays.asList("scheduleId", "reservations"));
        int index = 0;
        for (Roaster roaster : roasters) {
            int roaId = roaster.getId();
            int scheduleId = roaster.getScheduleId();
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
            Doctor doctor = doctorRepository.findById(roaster.getDoctorId()).orElse(null);
            User user = userRepository.findById(doctor.getUserId()).orElse(null);
            arr.get(index).getAsJsonObject().addProperty("doctorName", user.getLastName() + user.getFirstName());
            arr.get(index).getAsJsonObject().addProperty("day", schedule.getDay());
            arr.get(index).getAsJsonObject().addProperty("time", schedule.getTime());
            index++;
        }
        return new CommonResponse(arr, 200).toString();
    }

    @GetMapping(path = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getAllReservationsByHostpitalId(HttpServletResponse response,
                                                  @RequestParam(value = "hospitalId") int hospitalId,
                                                  @RequestHeader("Authorization") String header) {
        // TODO should refactor here, because fat controller design
        try {
            String token = header.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            User user = userRepository.findByUsername(username).orElse(null);
            List<Reservation> reservations = reservationRepository.findReservationByHospitalIdAndUserId(user.getId(), hospitalId);
            JsonIter ji = new JsonIter();
            JsonArray arr = ji.listIntoArrayWithoutKey(reservations, "roasterId");
            Gson g = new Gson();
            int index = 0;
            for (Reservation res : reservations) {
                int roaId = res.getRoasterId();
                Roaster roaster = roasterRepository.findById(roaId).get();
                int doctorId = roaster.getDoctorId();
                Doctor doctor = doctorRepository.findById(doctorId).get();
                String doctorName = userRepository.findById(doctor.getUserId()).get().getName();
                String userPhone = user.getPhone();
                String userName = user.getName();
                Pet pet = petRepository.findById(res.getPetId()).get();
                int scheduleId = roaster.getScheduleId();
                Schedule schedule = scheduleRepository.findById(scheduleId).get();
                String time = schedule.getDay() + " " + schedule.getTime();
                System.out.println("userid:" + user.getId() + " petid:" + pet.getId());
                MedicalRecord medicalRecord = medicalRecordRepository.findByUserIdAndPetId(user.getId(), pet.getId()).orElse(null);
//                for (JsonElement je : arr) {
                arr.get(index).getAsJsonObject().addProperty("doctorName", doctorName);
                arr.get(index).getAsJsonObject().addProperty("userName", userName);
                arr.get(index).getAsJsonObject().addProperty("userPhone", userPhone);
                arr.get(index).getAsJsonObject().addProperty("petName", pet.getName());
                arr.get(index).getAsJsonObject().addProperty("petAge", pet.getAge());
                arr.get(index).getAsJsonObject().addProperty("petSpecies", pet.getSpecies());
                arr.get(index).getAsJsonObject().addProperty("petBreed", pet.getBreed());
                arr.get(index).getAsJsonObject().addProperty("petOwnDate", pet.getOwnDate());
                arr.get(index).getAsJsonObject().addProperty("day", schedule.getDay());
                arr.get(index).getAsJsonObject().addProperty("time", schedule.getTime());
                JsonArray medicalTreatments = medicalRecord == null ? null : g.toJsonTree(medicalRecord).getAsJsonObject().get("medicalTreatments").getAsJsonArray();
                if (medicalRecord == null) {
                    arr.get(index).getAsJsonObject().addProperty("medicalRecordId", "null");
                } else {
                    arr.get(index).getAsJsonObject().addProperty("medicalRecordId", medicalRecord.getId());
                }
//                    JsonIter jii = new JsonIter();
//                    JsonArray descriptions = jii.listIntoArrayWithKeys(medicalTreatments, Arrays.asList("description"));
//                    if(descriptions.size() == 0){
//                        JsonObject jsonDes = new JsonObject();
//                        jsonDes.addProperty("description", "沒有診療紀錄");
//                        descriptions.add(jsonDes);
//                    }
//                    je.getAsJsonObject().add("medicalTreatments", descriptions);
                if (medicalTreatments == null) {
                    arr.get(index).getAsJsonObject().addProperty("medicalTreatments", "null");
                } else {
                    arr.get(index).getAsJsonObject().add("medicalTreatments", medicalTreatments);
                }
//                }
                System.out.println("DEBUG8");
                index++;
            }
            System.out.println("DEBUG9");
            return new CommonResponse(arr, 200).toString();
        } catch (ExpiredJwtException e) {
            response.setStatus(403);
            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
        }
    }

//    @GetMapping(path = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
//    public String getAllReservationsByHostpitalId(HttpServletResponse response,
//                                                  @RequestParam(value = "hospitalId") int hospitalId,
//                                                  @RequestHeader("Authorization") String header) {
//        try {
//            String token = header.substring(7);
//            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
//            User user = userRepository.findByUsername(username).get();
//            int userId = user.getId();
//            List<Reservation> reservations = reservationRepository.findReservationByUserIdAndHospitalId(userId, hospitalId);
//            JsonIter ji = new JsonIter();
//            JsonArray arr = ji.listIntoArrayWithoutKey(reservations, "roasterId");
//            int index = 0;
//            System.out.println("DEBUG reservation size:"+reservations.size());
//            for (Reservation res : reservations) {
//                int roaId = res.getRoasterId();
//                Roaster roaster = roasterRepository.findById(roaId).get();
//                int scheduleId = roaster.getScheduleId();
//                Schedule schedule = scheduleRepository.findById(scheduleId).get();
//                String time = schedule.getDay() + " " + schedule.getTime();
//                int doctorId = roaster.getDoctorId();
//                arr.get(index).getAsJsonObject().addProperty("day", schedule.getDay());
//                arr.get(index).getAsJsonObject().addProperty("time", schedule.getTime());
//                arr.get(index).getAsJsonObject().addProperty("doctorId", doctorId);
//                index++;
//            }
//            return new CommonResponse(arr, 200).toString();
//        } catch (ExpiredJwtException e) {
//            response.setStatus(403);
//            return new CommonResponse("token expired: " + e.getMessage(), 403).toString();
//        } catch (io.jsonwebtoken.MalformedJwtException e){
//            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
//        } catch (StringIndexOutOfBoundsException e){
//            return new CommonResponse("token format fail: " + e.getMessage(), 403).toString();
//        }
//    }
}