package com.jpacourse.service.impl;

import com.jpacourse.dto.MedicalTreatmentTO;
import com.jpacourse.mapper.MedicalTreatmentMapper;
import com.jpacourse.persistance.dao.MedicalTreatmentDao;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.service.MedicalTreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class MedicalTreatmentServiceImpl implements MedicalTreatmentService
{
    private final MedicalTreatmentDao medicalTreatmentDao;

    @Autowired
    public MedicalTreatmentServiceImpl(MedicalTreatmentDao pmedicalTreatmentDao)
    {
        medicalTreatmentDao = pmedicalTreatmentDao;
    }

    @Override
    public MedicalTreatmentTO findById(Long id) {
        final MedicalTreatmentEntity entity = medicalTreatmentDao.findOne(id);
        return MedicalTreatmentMapper.mapToTO(entity);
    }
}
