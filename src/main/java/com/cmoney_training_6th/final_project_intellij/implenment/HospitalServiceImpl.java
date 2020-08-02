package com.cmoney_training_6th.final_project_intellij.implenment;

import com.cmoney_training_6th.final_project_intellij.dao.CrewRepository;
import com.cmoney_training_6th.final_project_intellij.dao.HospitalRepository;
import com.cmoney_training_6th.final_project_intellij.model.Crew;
import com.cmoney_training_6th.final_project_intellij.model.Hospital;
import com.cmoney_training_6th.final_project_intellij.service.HospitalService;
import com.cmoney_training_6th.final_project_intellij.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private CrewRepository crewRepository;

    @Override
    public CommonResponse add(Hospital newHospital){
        try {
            Crew crew = new Crew();
            Hospital hospital = hospitalRepository.findByUserId(newHospital.getUserId()).orElse(null);
            if(hospital != null){
                int ownerId = hospital.getUserId();
                new CommonResponse("user already have a hospital:" + ownerId, 200).toString();
            }
            hospitalRepository.save(newHospital);
            if(crewRepository.findByUserIdAndHospitalId(hospital.getUserId(),
                    hospital.getId()).orElse(null) != null){
                new CommonResponse("user already is crew of this hospital", 200);
            }
            crew.setUserId(hospital.getUserId());
            crew.setHospitalId(hospital.getId());
            crewRepository.save(crew);
            return new CommonResponse("success", 200);
        } catch (DataIntegrityViolationException e) {
            return new CommonResponse("fail: " + e.getRootCause().getMessage(), 404);
        }
    }

    @Override
    public CommonResponse edit(int hospitalId) {
        return null;
    }

    @Override
    public CommonResponse delete(int hospitalId) {
        return null;
    }
}
