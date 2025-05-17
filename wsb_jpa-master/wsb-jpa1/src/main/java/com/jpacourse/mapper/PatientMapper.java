package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public final class PatientMapper
{

    @Autowired
    VisitService visitService;

    public static PatientTO mapToTO(final PatientEntity patientEntity)
    {
        if (patientEntity == null)
        {
            return null;
        }
        final PatientTO patientTO = new PatientTO();

        patientTO.setId(patientEntity.getId());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setAddress(AddressMapper.mapToTO(patientEntity.getAddress()));
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setBmi(patientEntity.getBmi());
        patientTO.setVisitsDone(patientEntity.getVisits().stream().filter(visitEntity -> visitEntity.getTime().isBefore(LocalDateTime.now())).map(VisitMapper::mapToTO).toList());

        return patientTO;
    }

    public static PatientEntity mapToEntity(final PatientTO patientTO)
    {
        if(patientTO == null)
        {
            return null;
        }

        final PatientEntity patientEntity = new PatientEntity();

        patientEntity.setId(patientTO.getId());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setAddress(AddressMapper.mapToEntity(patientTO.getAddress()));
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setBmi(patientTO.getBmi());


        return patientEntity;
    }
}
