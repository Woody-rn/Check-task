package ru.npepub.taskscanner.service;

import lombok.extern.slf4j.Slf4j;
import ru.npepub.taskscanner.entity.SprintEntity;
import ru.npepub.taskscanner.repository.SprintRepository;

@Slf4j
public class SprintService {
    private final SprintRepository sprintRepository;

    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    SprintEntity getOrCreate(Integer sprintNum) {
        log.debug("Requesting sprint: number={} ", sprintNum);

        return sprintRepository.findByNumber(sprintNum)
                .map(sprintEntity -> {
                    log.debug("Sprint found: id={} number={} ", sprintEntity.getId(), sprintEntity.getNumber());

                    return sprintEntity;
                })
                .orElseGet(() -> {
                    log.debug("Sprint not found, creating new: number={}", sprintNum);

                    SprintEntity newSprint = SprintEntity.builder()
                            .number(sprintNum)
                            .build();

                    SprintEntity saved = sprintRepository.save(newSprint);
                    log.debug("Sprint created: id={}, number={}", saved.getId(), sprintNum);
                    return saved;
                });
    }
}
