package emiya.commands;

import emiya.emiyaexception.EmptyEventException;
import emiya.emiyaexception.InvalidDateTimeException;
import emiya.emiyaexception.NoFromException;
import emiya.emiyaexception.NoToException;
import emiya.emiyaexception.UnknownCommandException;
import emiya.emiyaexception.WrongDateTimeFormatException;
import emiya.parser.Parser;
import emiya.storage.Storage;
import emiya.task.Event;
import emiya.task.TaskList;
import emiya.ui.Ui;

/**
 * A class that represents the Event command.
 */
public class EventCommand extends Command {

    /**
     * Creates an Event task object and adds the task into the task list.
     *
     * @param taskDetails A String that contains the input from the user.
     * @param parser The Parser instance associated with the task bot.
     * @param taskList The TaskList instance associated with the task bot.
     * @param storage The Storage instance associated with the task bot.
     * @param ui The Ui instance associated with the task bot.
     * @param fileName The name of the file that the newly updated task list will be written into.
     * @param dirName The name of the directory that the newly updated task list will be written into.
     * @return A String that indicates that the Event task has been successfully added
     *     into the task list.
     * @throws EmptyEventException An exception that is thrown when the user uses an event command but
     *     does not input in task details.
     * @throws NoFromException An exception that is thrown when the user does not use /from in their event command.
     * @throws NoToException An exception that is thrown when the user does not use /to in their event command.
     * @throws InvalidDateTimeException An exception that is thrown when the user input contains an invalid date.
     * @throws WrongDateTimeFormatException An exception that is thrown when the date that the user gives is
     *     in the wrong format.
     */
    public static String createEvent(String taskDetails, Parser parser, TaskList taskList, Storage storage,
                                      Ui ui, String fileName, String dirName) throws EmptyEventException,
            NoFromException, NoToException, InvalidDateTimeException, WrongDateTimeFormatException,
            UnknownCommandException {
        if (taskDetails.equals("")) {
            throw new EmptyEventException();
        }

        // parsedEventDetails are in the order of nameOfTask, start date, end date
        String[] parsedEventDetails = parser.parseForEvent(taskDetails);
        Event event = new Event(false, parsedEventDetails[0], parsedEventDetails[1], parsedEventDetails[2]);
        taskList.add(event);
        storage.writeToFileFromTaskList(taskList, fileName, dirName);
        if (taskList.size() == 1) {
            return ui.addedSingularMessage(event, taskList);
        } else {
            return ui.addedPluralMessage(event, taskList);
        }
    }

    @Override
    public String giveHelpDescription() {
        return "Event: This command creates an Event to help you keep track of any tasks with a start and end date!\n"
                + "The format for the input is as follows:\n"
                + "event NAME_OF_TASK /from START_TIME /to END_TIME,\n"
                + "where START_TIME and END_TIME is in the format YYYY-MM-DD TTTT.\n"
                + "Note that the time given has to be in 24 hour format!\n";
    }
}
