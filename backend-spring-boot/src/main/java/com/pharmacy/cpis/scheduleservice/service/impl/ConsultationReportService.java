package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.scheduleservice.repository.IConsultationReportRepository;
import com.pharmacy.cpis.scheduleservice.service.IConsultationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationReportService implements IConsultationReportService {

    @Autowired
    private IConsultationReportRepository consIConsultationReportRepository;
}
