package com.jpacourse.service;

import com.jpacourse.dto.DoctorTO;

import java.util.List;

public interface DoctorService {
    DoctorTO findById(final Long id);

    List<DoctorTO> findAll();
}
