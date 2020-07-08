package com.cmoney_training_6th.final_project_intellij.controller.user;

import com.cmoney_training_6th.final_project_intellij.model.*;
import com.cmoney_training_6th.final_project_intellij.repos.*;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.cmoney_training_6th.final_project_intellij.util.JwtUtil;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/hospital")
public class HospitalController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
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
    private JwtUtil jwtTokenUtil;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String acHello() {
        JsonObject newJson = new JsonObject();
        newJson.addProperty("new json test", 123456789);
        return new CommonResponse("heeehehehee", 200).toString();
    }

    @GetMapping(path = "/doctor", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getDoctorDetailByHostpitalId(@RequestParam(value = "hospital_id")
                                                       int hospitalId) {
        Gson g = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        JsonObject json = new JsonObject();
        JsonObject doctorJson;
        JsonArray doctorArr = new JsonArray();
        List<Doctor> doctor = doctorRepository.findByHospitalId(hospitalId);
        for (Doctor doc : doctor) {
            doctorJson = (JsonObject) g.toJsonTree(doc).getAsJsonObject();
            doctorArr.add(doctorJson);
        }
        json.add("doctors", doctorArr);
        return new CommonResponse(json, 200).toString();
    }

    @GetMapping(path = "/by/address_area", produces = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public String getHospitalByAddressArea(@RequestParam(value = "address_area")
                                                   String address_area) {
//        hospitalRepository.findBy
        return new CommonResponse("", 200).toString();
    }

    @PostMapping(path = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public String bookingByDoctorId(
            @RequestBody Reservation request,
            @RequestHeader("Authorization") String header) {
        try {
            String token = header.substring(7);
            String username = jwtTokenUtil.getUserNameFromJwtToken(token);
            Optional<User> user = userRepository.findByUsername(username);
            // 檢查是否已經預約過，若大於 1 則跳 NonUniqueResultException，等於 1 則需要 handle
            List<Reservation> reservation = reservationRepository.
                    findAllByRoasterIdAndDateAndUserId(request.getRoasterId(), request.getDate(), user.get().getId());
            if (reservation.size() >= 1) {
                int bookingNum = reservation.get(reservation.size() - 1).getNumber();
                return new CommonResponse("booked before, booking number is:" + bookingNum, 404).toString();
            }
            request.setUserId(user.get().getId());
            System.out.println("DEBUG user_id: " + user.get().getId());
            int reservePatientCnt = reservationRepository.findAllByRoasterIdAndDate(request.getRoasterId(), request.getDate()).size();
            System.out.println("DEBUG reservePatientCnt: " + reservePatientCnt);
            int bookingNum = reservePatientCnt + 1; // 預約這個班表且為同天的人數
            request.setNumber(bookingNum);
            reservationRepository.save(request);
            return new CommonResponse("reservation_id: " + bookingNum, 200).toString();
        } catch (NoSuchElementException e) {
            return new CommonResponse("booking fail because wrong value is given.", 404).toString();
        }
    }
}