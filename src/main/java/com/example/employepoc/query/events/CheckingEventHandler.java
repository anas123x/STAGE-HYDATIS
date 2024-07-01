package com.example.employepoc.query.events;

import com.example.employepoc.command.events.*;
import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.dto.Person;
import com.example.employepoc.query.rest.repository.CheckingQueryRepository;
import com.example.employepoc.query.rest.repository.PersonQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckingEventHandler implements CheckingEvenHandlerInterface {

    private final CheckingQueryRepository repository;
   private final PersonQueryRepository personQueryRepository;

    @Override
    public void on(CreateCheckingEvent event) {
        System.out.println("Checking created event received" + event.toString());

        // Convert the event's checking to your Checking entity
        Checking checkingEntity = new Checking();
        checkingEntity.setId(UUID.randomUUID().toString());
        checkingEntity.setActualTime(event.getChecking().getActualTime());
        checkingEntity.setLogicalTime(event.getChecking().getLogicalTime());
        checkingEntity.setData(event.getChecking().getData());
        checkingEntity.setDirectionGenerated(true);
        checkingEntity.setIgnoredByCalc(false);
        checkingEntity.setUserSetTime(event.getChecking().getActualTime());
        checkingEntity.setActualSource(com.example.employepoc.query.rest.dto.Checking.CheckingSource.valueOf(event.getChecking().getActualSource().name()));
        checkingEntity.setDirection(com.example.employepoc.query.rest.dto.Checking.CheckingDirection.valueOf(event.getChecking().getDirection().name()));
        checkingEntity.setUsed(false);
        checkingEntity.setTimesheetId(1001L);
        try {
            Person p = personQueryRepository.findById(event.getPersonId()).get();
            checkingEntity.setPerson(p);
            checkingEntity.setMatricule(p.getMatricule());
            repository.save(checkingEntity);
        }catch (Exception e){
            System.out.println(e);
        }

    }
    @Override
    public void on(PersonCheckingCreatedOrUpdatedEvent event) {

    }

    @Override
    public void on(PersonCheckingDeletedEvent event) {
        System.out.println("Checking deleted event received" + event.toString());
        boolean duplicate = event.isDuplicate();
        Checking checking = new Checking();
        checking.setActualTime(event.getChecking().getActualTime());
        checking.setActualSource(com.example.employepoc.query.rest.dto.Checking.CheckingSource.valueOf(event.getChecking().getActualSource().name()));
        checking.setDirection(com.example.employepoc.query.rest.dto.Checking.CheckingDirection.valueOf(event.getChecking().getDirection().name()));
        Person p = personQueryRepository.findById(event.getChecking().getPerson().getId()).get();
        checking.setPerson(p);
        if(duplicate)
        {
            List<Checking> checkings = repository.findByPersonIdAndActualTimeAndDirectionAndActualSource(
                    checking.getPerson().getId(),
                    checking.getActualTime(),
                    checking.getDirection(),
                    checking.getActualSource()
            );
            if (checkings.size() > 1) {
              for  (Checking c:  checkings) {
                c.setDeleted(true);
                repository.save(c);
              }
            }
            else
            {
                checkings.get(0).setDeleted(true);
                repository.save(checkings.get(0));
            }
        }
        else
        {
            checking.setDeleted(true);
            repository.save(checking);
        }

    }

    @Override
    public void on(PersonsCheckingCreatedWithCollectiveEvent event) {

    }

    @Override
    public void on(PersonsCheckingCreatedEvent event) {

    }
}
