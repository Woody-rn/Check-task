package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.SprintEntity;
import ru.npepub.taskscanner.repository.SprintRepository;

public class SprintService {
    private final SprintRepository sprintRepository;

    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public SprintEntity getOrCreate(Integer sprintNum) {
        return sprintRepository.findByNumber(sprintNum)
                .orElseGet(() -> {
                    SprintEntity newSprint = SprintEntity.builder()
                            .number(sprintNum)
                            .build();

                    return sprintRepository.save(newSprint);
                });
    }
}
