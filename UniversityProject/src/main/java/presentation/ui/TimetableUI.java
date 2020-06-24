package presentation.ui;

import business.services.TimetableService;
import model.dto.Course;
import model.dto.Teacher;
import model.dto.Timetable;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class TimetableUI {
    private TimetableService timetableService = new TimetableService();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            menu();
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 0: {
                    AppUI.start();
                    break;
                }
                case 1: {
                    addTimetable();
                    break;
                }
                case 2: {
                    viewTimetables();
                    break;
                }
                case 3: {
                    updateTimetable();
                    break;
                }
                case 4: {
                    deleteTimetable();
                    break;
                }
            }
        }
    }

    private void deleteTimetable() {
        System.out.println("Enter ID to delete: ");
        Timetable timetable = new Timetable();
        timetable = timetableService.findTimetable(timetable, scanner.nextInt());
        timetableService.deleteTimetable(timetable);
    }

    private void updateTimetable() {
        viewTimetables();
        System.out.print("Enter ID to update: ");
        Timetable timetable = new Timetable();
        timetable = timetableService.findTimetable(timetable, scanner.nextInt());
        scanner.nextLine();
        System.out.print("Enter new begin date: ");
        String begin = scanner.next();
        LocalDateTime beginTime = LocalDateTime.parse(begin);
        System.out.print("Enter new end date: ");
        String end = scanner.next();
        LocalDateTime endTime = LocalDateTime.parse(end);
        System.out.println("Enter new date: ");
        String date = scanner.next();
        LocalDate localDate = LocalDate.parse(date);
        timetable.setBegin(beginTime);
        timetable.setEnd(endTime);
        timetable.setDate(localDate);
        timetableService.updateTimetable(timetable);

    }

    private void viewTimetables() {
        Timetable timetable = new Timetable();
        List<Timetable> timetables = timetableService.getTimetables(timetable);
        if (!timetables.isEmpty()) {
            timetables.forEach(timetab -> {
                System.out.println(timetab.getTimetableID() + ". " + timetab.getBegin().getHour() + " : " + timetab.getBegin().getMinute()
                        + " - " + timetab.getBegin().getDayOfMonth() +"."+ timetab.getBegin().getMonth() + "." + timetab.getBegin().getMonth());
                List<Course> courses = timetab.getCourses();
                if (courses != null) {
                    System.out.println("Courses - ");
                    courses.forEach(course -> {
                        System.out.print(course.getName() + "\n   " +
                                course.getDescription());
                    });
                } else {
                    System.out.println();
                }
            });
        } else {
            System.out.println("No teachers available yet.");
        }
    }

    private void addTimetable() {
        Timetable timetable = new Timetable();
        System.out.print("Enter begin date: ");
        String begin = scanner.next();
        LocalDateTime beginTime = LocalDateTime.parse(begin);
        System.out.print("Enter end date: ");
        String end = scanner.next();
        LocalDateTime endTime = LocalDateTime.parse(end);
        System.out.println("Enter date: ");
        String date = scanner.next();
        LocalDate localDate = LocalDate.parse(date);
        timetable.setBegin(beginTime);
        timetable.setEnd(endTime);
        timetable.setDate(localDate);
        timetableService.addTimetable(timetable);
    }

    private void menu() {
        System.out.println("TIMETABLE MANAGEMENT " +
                "\n0.Exit " +
                "\n1.Add Timetable " +
                "\n2.View Timetables " +
                "\n3.Update Timetable " +
                "\n4.Delete Timetable\n");
    }
}