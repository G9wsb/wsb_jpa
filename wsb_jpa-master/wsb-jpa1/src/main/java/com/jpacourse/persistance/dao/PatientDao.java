package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.PatientEntity;

import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long>
{
    void addVisit(Long patientId, Long doctorId, String date, String description);

    List<PatientEntity> findByLastName(String lastName);

    List<PatientEntity> findWithVisitsCountGreaterThan(Integer visitsCount);

    List<PatientEntity> findWithBMIHigherThan(Float bmi);
}
