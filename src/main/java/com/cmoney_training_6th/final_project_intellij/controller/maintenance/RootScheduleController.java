package com.cmoney_training_6th.final_project_intellij.controller.maintenance;

import com.cmoney_training_6th.final_project_intellij.model.Schedule;
import com.cmoney_training_6th.final_project_intellij.model.User;
import com.cmoney_training_6th.final_project_intellij.repos.DoctorRepository;
import com.cmoney_training_6th.final_project_intellij.repos.ScheduleRepository;
import com.cmoney_training_6th.final_project_intellij.repos.UserRepository;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;

//@Controller // This means that this class is a Controller
@RestController // 用這個就不用每個 request 加上 ResponsBody 才可以回傳 json
@RequestMapping(path = "/maintenance/schedule")
public class RootScheduleController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String acHello(@RequestParam String utf8) {
        return "account hello 中文測試" + utf8;
    }

    @PostMapping(path = "/setup", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findDoctorByUserId(HttpServletResponse response) throws Exception{
        scheduleRepository.deleteAll();
        ArrayList<String> allDay;
        ArrayList<String> allTime;
        allDay = new ArrayList<String>();
        allDay.add("Monday");
        allDay.add("Tuesday");
        allDay.add("Wednesday");
        allDay.add("Thursday");
        allDay.add("Friday");
        allDay.add("Saturday");
        allDay.add("Sunday");
        allTime = new ArrayList<String>();
        allTime.add("Day");
        allTime.add("Afternoon");
        allTime.add("Night");
        int id = 1;
        for(String day : allDay){
            for(String time : allTime){
                Schedule schedule = new Schedule();
                schedule.setId(id++);
                schedule.setDay(day);
                schedule.setTime(time);
                System.out.println("day:"+schedule.getDay());
                System.out.println("time:"+schedule.getTime());
                try {
                    scheduleRepository.save(schedule);
                }catch (DataIntegrityViolationException e) {
                    return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404).toString();
                }
            }
        }
        return new CommonResponse("setup done", 200).toString();
    }

}