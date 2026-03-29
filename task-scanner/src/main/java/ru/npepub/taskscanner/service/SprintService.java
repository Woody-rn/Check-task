package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.Sprint;
import ru.npepub.taskscanner.repository.SprintRepository;

public class SprintService {
    private final SprintRepository sprintRepository;

    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public Sprint getOrCreate(Long sprintNum) {
        return sprintRepository.findByNumber(sprintNum)
                .orElseGet(() -> {
                    Sprint newSprint = Sprint.builder()
                            .number(sprintNum)
                            .build();

                    return sprintRepository.save(newSprint);
                });
    }
}
