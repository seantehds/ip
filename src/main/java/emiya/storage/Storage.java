package emiya.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import emiya.emiyaexception.CreateDirectoryFailException;
import emiya.emiyaexception.InvalidDateException;
import emiya.emiyaexception.WrongDateFormatException;
import emiya.task.Deadline;
import emiya.task.Event;
import emiya.task.Task;
import emiya.task.TaskList;
import emiya.task.ToDo;

public class Storage {
    public Storage() {

    }

    public String fileContents(String fileName, String dirName) {
        String path = Paths.get("").toAbsolutePath().toString();
        String pathToDataDir = Paths.get(path, dirName).toString();
        Path pathToDataDoc = Paths.get(pathToDataDir, fileName);
        String res = "";
        try {
            byte[] bytes = Files.readAllBytes(pathToDataDoc);
            String content = new String(bytes);
            res = content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void createDirectory(String dirName) throws CreateDirectoryFailException {
        String path = Paths.get("").toAbsolutePath().toString();
        String pathToDataDir = Paths.get(path, dirName).toString();
        File dataDir = new File(pathToDataDir);

        if (!dataDir.exists()) {
            boolean result = dataDir.mkdirs();
            if (!result) {
                throw new CreateDirectoryFailException();
            }
        }
    }

    public void createFileInDirectory(String fileName, String dirName) {
        String path = Paths.get("").toAbsolutePath().toString();
        String pathToDataDir = Paths.get(path, dirName).toString();
        Path pathToDataDoc = Paths.get(pathToDataDir, fileName);
        String pathToDataDocStr = pathToDataDoc.toString();

        File dataDoc = new File(pathToDataDocStr);

        if (!dataDoc.exists()) {
            String testData = "";
            try {
                Files.write(pathToDataDoc, testData.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFileFromTaskList(ArrayList<Task> taskArrayList, String fileName, String dirName) {
        String path = Paths.get("").toAbsolutePath().toString();
        String pathToDataDir = Paths.get(path, dirName).toString();
        Path pathToDataDoc = Paths.get(pathToDataDir, fileName);

        StringBuilder str = new StringBuilder();
        for (Task task : taskArrayList) {
            str.append(task.typeOfString());
            str.append("| ");
            str.append(task.printStatusString());
            str.append("| ");
            str.append(task.printTaskDetailsString());
            str.append("\n");
        }

        try {
            Files.write(pathToDataDoc, str.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToFileFromTaskList(TaskList taskList, String fileName, String dirName) {
        String path = Paths.get("").toAbsolutePath().toString();
        String pathToDataDir = Paths.get(path, dirName).toString();
        Path pathToDataDoc = Paths.get(pathToDataDir, fileName);

        StringBuilder str = new StringBuilder();
        for (Task task : taskList.getTaskArrayList()) {
            str.append(task.typeOfString());
            str.append("| ");
            str.append(task.printStatusString());
            str.append("| ");
            str.append(task.printTaskDetailsString());
            str.append("\n");
        }

        try {
            Files.write(pathToDataDoc, str.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void fillListWithFileContent(TaskList taskList, String fileContent)
            throws WrongDateFormatException, InvalidDateException {
        String[] tasksStrArr = fileContent.split("\n");

        for (String tasksStr : tasksStrArr) {
            if (tasksStr.isEmpty()) {
                continue;
            }
            String[] tasksStrParts = tasksStr.split(" \\| ");
            String taskType = tasksStrParts[0];
            int isCompletedInt = Integer.parseInt(tasksStrParts[1]);
            boolean isCompletedBool = (isCompletedInt == 1);
            String taskDetails = tasksStrParts[2];
            String firstDate = "";
            String secondDate = "";
            if (tasksStrParts.length >= 4) {
                firstDate = tasksStrParts[3];
            }
            if (tasksStrParts.length == 5) {
                secondDate = tasksStrParts[4];
            }

            if (taskType.equals("T")) {
                taskList.add(new ToDo(isCompletedBool, taskDetails));
            } else if (taskType.equals("D")) {
                taskList.add(new Deadline(isCompletedBool, taskDetails, firstDate));
            } else {
                taskList.add(new Event(isCompletedBool, taskDetails, firstDate, secondDate));
            }
        }
    }
}
