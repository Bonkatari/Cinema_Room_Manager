package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int rows;
        int seats;

        do {
            System.out.println("Enter the number of rows (max 9):");
            System.out.print("> ");
            rows = sc.nextInt();
            if (rows > 9) {
                System.out.println("The number of rows cannot exceed 9.");
            }
        } while (rows > 9);

        do {
            System.out.println("Enter the number of seats in each row (max 9):");
            System.out.print("> ");
            seats = sc.nextInt();
            if (seats > 9) {
                System.out.println("The number of seats in each row cannot exceed 9.");
            }
        } while (seats > 9);

        char[][] cinema = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                cinema[i][j] = 'S';
            }
        }

        int purchasedTickets = 0;
        int currentIncome = 0;
        int totalIncome = calculateTotalIncome(rows, seats);

        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            System.out.print("> ");
            int choice = sc.nextInt();

            if (choice == 1) {
                printCinema(cinema);
            } else if (choice == 2) {
                int[] result = buyTicket(cinema, rows, seats, sc);
                purchasedTickets++;
                currentIncome += result[0];
            } else if (choice == 3) {
                printStatistics(rows, seats, purchasedTickets, currentIncome, totalIncome);
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static int calculateTotalIncome(int rows, int seats) {
        if (rows * seats <= 60) {
            return rows * seats * 10;
        } else {
            int frontHalfRows = rows / 2;
            int backHalfRows = rows - frontHalfRows;
            return frontHalfRows * seats * 10 + backHalfRows * seats * 8;
        }
    }

    private static void printCinema(char[][] cinema) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 1; i <= cinema[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < cinema.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[] buyTicket(char[][] cinema, int rows, int seats, Scanner sc) {
        int rowNumber;
        int seatNumber;

        while (true) {
            System.out.println("Enter a row number:");
            System.out.print("> ");
            rowNumber = sc.nextInt();

            System.out.println("Enter a seat number in that row:");
            System.out.print("> ");
            seatNumber = sc.nextInt();

            if (rowNumber < 1 || rowNumber > rows || seatNumber < 1 || seatNumber > seats) {
                System.out.println("Wrong input! Please enter a valid seat.");
            } else if (cinema[rowNumber - 1][seatNumber - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                break;
            }
        }

        int ticketPrice;
        if (rows * seats <= 60) {
            ticketPrice = 10;
        } else {
            int frontHalfRows = rows / 2;
            if (rowNumber <= frontHalfRows) {
                ticketPrice = 10;
            } else {
                ticketPrice = 8;
            }
        }
        System.out.println("Ticket price: $" + ticketPrice);
        cinema[rowNumber - 1][seatNumber - 1] = 'B';
        return new int[]{ticketPrice};
    }

    private static void printStatistics(int rows, int seats, int purchasedTickets, int currentIncome, int totalIncome) {
        double percentage = (double) purchasedTickets / (rows * seats) * 100;
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }
}