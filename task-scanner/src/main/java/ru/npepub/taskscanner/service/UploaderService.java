package ru.npepub.taskscanner.service;

import ru.npepub.taskscanner.entity.FileMetaData;
import ru.npepub.taskscanner.entity.Sprint;
import ru.npepub.taskscanner.entity.Task;
import ru.npepub.taskscanner.repository.FileMetaDataRepository;
import ru.npepub.taskscanner.repository.SprintRepository;
import ru.npepub.taskscanner.repository.TaskRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UploaderService {
    private static final Pattern PATTERN = Pattern.compile(".*?sprint_(\\d+).*?task_(\\d+).*");
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final FileMetaDataRepository fileMetaDataRepository;


    public UploaderService(SprintRepository sprintRepository, TaskRepository taskRepository, FileMetaDataRepository fileMetaDataRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
        this.fileMetaDataRepository = fileMetaDataRepository;
    }

    public void scanFiles(String pathToDirectory) {
        Path startPath = Path.of(pathToDirectory);
        try (Stream<Path> stream = Files.walk(startPath)) {
            List<Path> validFiles = stream.filter(Files::isRegularFile)
                    .filter(this::isValidTxtFile)
                    .map(startPath::relativize)
                    .filter(this::isValidPathToFile)
                    .toList();
            saveFilesToDatabase(validFiles, startPath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidTxtFile(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.endsWith(".txt");
    }

    private boolean isValidPathToFile(Path path) {
        String string = path.toString();
        return PATTERN.matcher(string).matches();
    }


    private void saveFilesToDatabase(List<Path> relativePaths, Path basePath) {
        for (Path relativePath : relativePaths) {
            String pathString = relativePath.toString();
            Matcher matcher = PATTERN.matcher(pathString);

            if (matcher.matches()) {
                Long sprintNum = Long.parseLong(matcher.group(1));
                Long taskNum = Long.parseLong(matcher.group(2));

                // Извлекаем имя задачи (папка после task)
                //String taskName = extractTaskName(relativePath);

                // Получаем или создаем sprint
                Sprint sprint = sprintRepository.findByNumber(sprintNum)
                        .orElseGet(() -> {
                            Sprint newSprint = new Sprint();
                            newSprint.setNumber(sprintNum);
                            return sprintRepository.save(newSprint);
                        });

                // Получаем или создаем task
                Task task = taskRepository
                        .findBySprintIdAndTaskNumber(sprint.getId(), taskNum)
                        .orElseGet(() -> {
                            Task newTask = new Task();
                            newTask.setSprintId(sprint.getId());
                            newTask.setNumber(taskNum);
                            return taskRepository.save(newTask);
                        });

                // Создаем файл
                FileMetaData fileMetaData = new FileMetaData();
                fileMetaData.setTaskId(task.getId());
                fileMetaData.setSprintId(sprint.getId());
                fileMetaData.setFileName(relativePath.getFileName().toString());
                fileMetaData.setS3Key("s3Key");
                fileMetaData.setS3Url(relativePath.toString());

                fileMetaDataRepository.save(fileMetaData);
            }
        }
    }
}
