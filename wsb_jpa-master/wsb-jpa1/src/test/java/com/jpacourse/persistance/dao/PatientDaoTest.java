package com.jpacourse.persistance.dao;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.DoctorService;
import com.jpacourse.service.PatientService;
import com.jpacourse.service.VisitService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PatientDaoTest
{
    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private VisitDao visitDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Transactional
    @Test
    public void shouldCreateVisitTest() {
        // given
        PatientEntity patientEntity = patientDao.findOne(1L);
        DoctorEntity doctorEntity = doctorDao.findOne(1L);
        VisitEntity visitEntityBefore = visitDao.findOne(7L);
        assertThat(visitEntityBefore).isNull();

        // when
        patientDao.addVisit(1L, 1L, "2025-05-25T10:00:00", "nowa wizyta");

        // then
        List<VisitEntity> patientVisitEntity = patientEntity.getVisits().stream().filter(visitEntity -> visitEntity.getId().equals(5L)).toList();
        List<VisitEntity> doctorVisitEntity = doctorEntity.getVisits().stream().filter(visitEntity -> visitEntity.getId().equals(5L)).toList();
        assertThat(patientVisitEntity.size()).isEqualTo(1);
        assertThat(doctorVisitEntity.size()).isEqualTo(1);
        VisitEntity visitEntityAfter = visitDao.findOne(7L);
        assertThat(visitEntityAfter).isNotNull();
    }

    @Transactional
    @Test
    public void shouldFindPatientByLastName() {
        // given

        // when
        List<PatientEntity> patientList = patientDao.findByLastName("Lis");

        // then
        assertThat(patientList).isNotNull();
        assertThat(patientList.size()).isEqualTo(1);
    }

    @Transactional
    @Test
    public void shouldFindPatientsWithMoreThanOneVisits() {
        // given

        // when
        List<PatientEntity> patientList = patientDao.findWithVisitsCountGreaterThan(1);

        // then
        assertThat(patientList).isNotNull();
        assertThat(patientList.size()).isEqualTo(1);
        assertThat(patientList.get(0).getLastName()).isEqualTo("Zając");
    }

    @Transactional
    @Test
    public void shouldFindPatientsWithBMIHigherThan25() {
        // given

        // when
        List<PatientEntity> patientList = patientDao.findWithBMIHigherThan(25f);

        // then
        assertThat(patientList).isNotNull();
        assertThat(patientList.size()).isEqualTo(1);
        assertThat(patientList.get(0).getLastName()).isEqualTo("Lis");
    }


    @Test
    void shouldThrowOptimisticLockExceptionWhenModifyingSameEntity() {
        TransactionTemplate tx = new TransactionTemplate(transactionManager);

        // 1. Zapis pacjenta
        Long patientId = tx.execute(status -> {
            PatientEntity patient = new PatientEntity();
            patient.setFirstName("Jan");
            patient.setLastName("Nowak");
            patient.setTelephoneNumber("123456789");
            patient.setPatientNumber("P001");
            patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
            patient.setBmi(22.5f);
            patientDao.save(patient);
            return patient.getId();
        });

        // 2. Pobierz dwie niezależne wersje pacjenta
        PatientEntity[] p1 = new PatientEntity[1];
        PatientEntity[] p2 = new PatientEntity[1];

        tx.execute(status -> {
            p1[0] = patientDao.findOne(patientId);
            return null;
        });

        tx.execute(status -> {
            p2[0] = patientDao.findOne(patientId);
            return null;
        });

        // 3. Zmodyfikuj p1 – wersja podskoczy do 1
        tx.execute(status -> {
            p1[0].setFirstName("Marek");
            patientDao.update(p1[0]);
            return null;
        });

        // 4. Spróbuj zapisać przestarzałego p2 – oczekiwany konflikt
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            patientDao.update(p2[0]);
            entityManager.flush(); // MUSI być tutaj
        });
    }
}
