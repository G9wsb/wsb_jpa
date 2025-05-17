package com.jpacourse.service.impl;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class VisitServiceImpl implements VisitService
{
    private final VisitDao visitDao;

    @Autowired
    public VisitServiceImpl(VisitDao pVisitDao)
    {
        visitDao = pVisitDao;
    }

    @Override
    public VisitTO findById(Long id) {
        final VisitEntity entity = visitDao.findOne(id);
        return VisitMapper.mapToTO(entity);
    }


    @Override
    public List<VisitTO> findByPatientId(Long id) {
        return visitDao.findAll().stream()
                .filter(visitEntity -> visitEntity.getPatient().getId().equals(id))
                .map(VisitMapper::mapToTO)
                .toList();
    }

    @Override
    public List<VisitTO> findCompletedVisitsByPatientId(Long patientId) {
        return visitDao.findAll().stream()
                .filter(visitEntity -> visitEntity.getPatient().getId().equals(patientId) && visitEntity.getTime().isBefore(LocalDateTime.now()))
                .map(VisitMapper::mapToTO)
                .toList();
    }
}
