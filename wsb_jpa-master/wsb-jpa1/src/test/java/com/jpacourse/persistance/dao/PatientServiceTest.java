package com.jpacourse.persistance.dao;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.service.DoctorService;
import com.jpacourse.service.PatientService;
import com.jpacourse.service.VisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientServiceTest
{
    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private VisitService visitService;

    @Transactional
    @Test
    public void testShouldGetCorrectData() {
        // given
        // when
        PatientTO patientTO = patientService.findById(1L);

        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getId()).isEqualTo(1L);
        assertThat(patientTO.getPatientNumber()).isEqualTo("PAT001");
        assertThat(patientTO.getEmail()).isEqualTo("marek.zajac@example.com");
        assertThat(patientTO.getFirstName()).isEqualTo("Marek");
        assertThat(patientTO.getLastName()).isEqualTo("Zając");
        assertThat(patientTO.getDateOfBirth()).isEqualTo("1990-05-12");
        assertThat(patientTO.getTelephoneNumber()).isEqualTo("111222333");

        assertThat(patientTO.getAddress().getAddressLine1()).isEqualTo("Ul. Krótka 2");
        assertThat(patientTO.getAddress().getAddressLine2()).isEqualTo("Mieszkanie 5");
        assertThat(patientTO.getAddress().getCity()).isEqualTo("Krakow");
        assertThat(patientTO.getAddress().getPostalCode()).isEqualTo("30-002");
        assertThat(patientTO.getBmi()).isEqualTo(20);

    }

    @Transactional
    @Test
    public void testShouldDeletePatientWithoutDeletingDoctors() {
        // given
        List<DoctorTO> doctorTOs = doctorService.findAll();

        // when
        patientService.deleteById(1L);
        List<DoctorTO> doctorsAfterDelete = doctorTOs.stream().map(doctorTO -> doctorService.findById(doctorTO.getId())).toList();
        List<VisitTO> visitTOsAfterDelete = visitService.findByPatientId(1L);


        // then
        assertThat(doctorsAfterDelete.size()).isEqualTo(doctorTOs.size());
        assertThat(visitTOsAfterDelete.size()).isEqualTo(0);
    }

    @Transactional
    @Test
    public void testShouldFindPatientsVisitsByPatientsId() {
        // given

        // when
        List<VisitTO> visits = patientService.findVisitsByPatientId(1L);


        // then
        assertThat(visits.size()).isEqualTo(4);
        assertThat(visits.get(0).getDescription()).isEqualTo("Kontrola");
        assertThat(visits.get(1).getDescription()).isEqualTo("Szczepienie");
        assertThat(visits.get(2).getDescription()).isEqualTo("Szczepienie 2 dawka");
        assertThat(visits.get(3).getDescription()).isEqualTo("Szczepienie 3 dawka");
    }

}
