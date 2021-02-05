package com.pharmacy.cpis.scheduleservice.controller;
import com.pharmacy.cpis.scheduleservice.dto.WorkingTimesDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "api/workingTimes")
@RestController
public class WorkingTimesController {
    @Autowired
    private IWorkingTimesService workingTimesService;

    @GetMapping
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<WorkingTimesDTO>> getAllWorkingTimes() {

        List<WorkingTimes> workingTimes = workingTimesService.findAll();

        List<WorkingTimesDTO> workingTimesDTOSs = new ArrayList<>();
        for (WorkingTimes wt : workingTimes) {
            workingTimesDTOSs.add(new WorkingTimesDTO(wt));
        }

        return new ResponseEntity<>(workingTimesDTOSs, HttpStatus.OK);
    }
}
