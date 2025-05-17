package com.jpacourse.mapper;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.dto.MedicalTreatmentTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.dao.impl.DoctorDaoImpl;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.TreatmentType;

import java.util.Optional;

public final class VisitMapper
{

    public static VisitTO mapToTO(final VisitEntity visitEntity)
    {
        if (visitEntity == null)
        {
            return null;
        }


        final VisitTO visitTO = new VisitTO();

        visitTO.setId(visitEntity.getId());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setTime(visitEntity.getTime());

        visitTO.setTreatments(visitEntity.getTreatments().stream().map(MedicalTreatmentMapper::mapToTO).toList());
        visitTO.setTreatmentNames(visitEntity.getTreatments().stream().map(MedicalTreatmentEntity::getType).toList());

        return visitTO;
    }

    public static VisitEntity mapToEntity(final VisitTO visitTO)
    {
        if(visitTO == null)
        {
            return null;
        }

        final VisitEntity visitEntity = new VisitEntity();

        visitEntity.setId(visitTO.getId());
        visitEntity.setDescription(visitTO.getDescription());
        visitEntity.setTime(visitTO.getTime());
        visitEntity.setTreatments(visitTO.getTreatments().stream().map(MedicalTreatmentMapper::mapToEntity).toList());

        return visitEntity;
    }
}
