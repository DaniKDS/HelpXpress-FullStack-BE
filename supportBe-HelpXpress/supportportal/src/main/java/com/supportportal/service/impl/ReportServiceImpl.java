package com.supportportal.service.impl;

import com.supportportal.domain.Organization;
import com.supportportal.domain.Report;
import com.supportportal.domain.User;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.ReportRepository;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public void addBulkReports() {
        List<User> users = userRepository.findAll();
        List<Organization> organizations = organizationRepository.findAll();
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            Report report = new Report();
            report.setUser(users.get(random.nextInt(users.size())));
            report.setOrganization(organizations.get(random.nextInt(organizations.size())));
            report.setProblemType("Problema " + (i + 1));
            report.setStatus("Nerezolvată");
            report.setReportTime(new Date());

            reportRepository.save(report);
        }
    }
}