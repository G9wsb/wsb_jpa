package com.jpacourse.mapper;

import com.jpacourse.dto.MedicalTreatmentTO;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;

public class MedicalTreatmentMapper {

    public static MedicalTreatmentTO mapToTO(final MedicalTreatmentEntity medicalTreatmentEntity)
    {
        if (medicalTreatmentEntity == null)
        {
            return null;
        }
        final MedicalTreatmentTO medicalTreatmentTo = new MedicalTreatmentTO();

        medicalTreatmentTo.setId(medicalTreatmentEntity.getId());
        medicalTreatmentTo.setType(medicalTreatmentEntity.getType());
        medicalTreatmentTo.setDescription(medicalTreatmentEntity.getDescription());

        return medicalTreatmentTo;
    }

    public static MedicalTreatmentEntity mapToEntity(final MedicalTreatmentTO medicalTreatmentTo)
    {
        if(medicalTreatmentTo == null)
        {
            return null;
        }

        final MedicalTreatmentEntity medicalTreatmentEntity = new MedicalTreatmentEntity();

        medicalTreatmentEntity.setId(medicalTreatmentTo.getId());
        medicalTreatmentEntity.setType(medicalTreatmentTo.getType());
        medicalTreatmentEntity.setDescription(medicalTreatmentTo.getDescription());

        return medicalTreatmentEntity;
    }
}
