package com.jpacourse.service;

import com.jpacourse.dto.AddressTO;
import com.jpacourse.dto.VisitTO;
import java.util.List;

public interface VisitService
{
    VisitTO findById(final Long id);

    List<VisitTO> findByPatientId(final Long patientId);

    List<VisitTO> findCompletedVisitsByPatientId(Long patientId);
}
