package com.jpacourse.persistance.dao.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {


    @Autowired
    DoctorDao doctorDao;

    @Autowired
    VisitDao visitDao;

    @Override
    public void addVisit(Long patientId, Long doctorId, String time, String description) {
        VisitEntity visitEntity = new VisitEntity();

        visitEntity.setPatient(this.findOne(patientId));
        visitEntity.setDoctor(doctorDao.findOne(doctorId));
        visitEntity.setTime(LocalDateTime.parse(time));
        visitEntity.setDescription(description);

        visitDao.save(visitEntity);
    }

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
        return this.findAll().stream().filter(patientEntity -> patientEntity.getLastName().equals(lastName)).toList();
    }

    @Override
    public List<PatientEntity> findWithVisitsCountGreaterThan(Integer visitsCount) {
        return this.findAll().stream().filter(patientEntity -> {
            PatientTO patientTO = PatientMapper.mapToTO(patientEntity);
            return patientTO.getVisitsDone().size()>visitsCount;
        }).toList();
    }

    @Override
    public List<PatientEntity> findWithBMIHigherThan(Float bmi) {
        return this.findAll().stream().filter(patientEntity -> patientEntity.getBmi() > bmi).toList();
    }
}
