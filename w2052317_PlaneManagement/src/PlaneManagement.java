import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Scanner;

public class PlaneManagement {
    static int[] rowA = new int[14];
    static int[] rowB = new int[12];
    static int[] rowC = new int[12];
    static int[] rowD = new int[14]; //4 arrays for 4 rows
    static int[][] rows = {rowA, rowB, rowC, rowD}; // created an array using above 4 arrays
    static int nextTicket = 0; //static int value for adjust length of ticketArray
    static Ticket[] ticketsArray = new Ticket[(nextTicket + 1)]; // array for save tickets

    static void buy_seat() {   //buy_seat method
        System.out.println("""
            Ticket Prices \s
                           1 - 5  Row seats - £200
                           6 - 9 Row seats  - £150
                           9 -12/14         - £100
            """);
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Input a row(A,B,C,D)");
            String row = scanner.nextLine().toUpperCase();

            if (Objects.equals(row, "A") ||
                    Objects.equals(row, "B") ||
                    Objects.equals(row, "C") ||
                    Objects.equals(row, "D")) {

                try {
                    System.out.println("Enter seat number");
                    int seat = scanner.nextInt() - 1;
                    if (Objects.equals(row, "A") && seat < rowA.length && rowA[seat] == 0) {
                        rowA[seat] = 1;
                        set_ticket(row, seat, set_price(seat), get_details()); //creating ticket,and set the ticket price
                        return;
                    } else if (Objects.equals(row, "B") && seat < rowB.length && rowB[seat] == 0) {
                        rowB[seat] = 1;
                        set_ticket(row, seat,set_price(seat), get_details());
                        return;
                    } else if (Objects.equals(row, "C") && seat < rowC.length && rowC[seat] == 0) {
                        rowC[seat] = 1;
                        set_ticket(row, seat, set_price(seat), get_details());
                        return;
                    } else if (Objects.equals(row, "D") && seat < rowD.length && rowD[seat] == 0) {
                        rowD[seat] = 1;
                        set_ticket(row, seat, set_price(seat), get_details());
                        return;
                    } else {
                        System.out.println("seat is booked or invalid seat number!");
                        if(exit_loop()){ // method for exit from the buy_seat method
                            return;
                        }
                    }

                } catch (Exception ex) {
                    System.out.println("Enter a number!");
                }
            } else {
                System.out.println("Invalid row,Enter A,B,C or D");
            }
        }
    }
    static boolean exit_loop() { //this method use to exit from loops
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println(" Do you want to try again(y/n)");
        String tryAgain = scanner.nextLine();
        if (Objects.equals(tryAgain, "n")) {
            return true;
        } else if (Objects.equals(tryAgain, "y")) {
            return false;
        } else {
            System.out.println("Enter y or n!");
        }
    }
}
    static void cancel_seat() { //method for cancel a booked seat
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input a row(A,B,C,D)");
            String row = scanner.nextLine().toUpperCase();
            int seat=0;

            if (Objects.equals(row, "A") ||
                    Objects.equals(row, "B") ||
                    Objects.equals(row, "C") ||
                    Objects.equals(row, "D")) {

                try {
                    if (Objects.equals(row, "A")&& seat<rowA.length) {
                        System.out.println("Enter seat number");
                        seat = scanner.nextInt() - 1;
                        if(seat> rowA.length){
                            System.out.println("Invalid seat number!");
                            if (exit_loop()) {
                                return;
                            }
                        }else {
                            if (rowA[seat] == 1) {
                                cancelAndAdjust(seat, rowA, row);
                                break;
                            } else {
                                System.out.println("This seat is not booked!");
                                if (exit_loop()) {
                                    return;
                                }
                            }
                        }
                    } else if (Objects.equals(row, "B") && seat<rowB.length) {
                        System.out.println("Enter seat number");
                        seat = scanner.nextInt() - 1;
                        if(seat> rowA.length){
                            System.out.println("Invalid seat number!");
                            if (exit_loop()) {
                                return;
                            }
                        }else {
                            if (rowB[seat] == 1) {
                                cancelAndAdjust(seat, rowB, row); //this method use to decrease ticketsArray length
                                break;                            //by one and remove the cancelled seat
                            } else {
                                System.out.println("Invalid seat number!");
                                if (exit_loop()) {
                                    return;
                                }
                            }
                        }
                    } else if (Objects.equals(row, "C")&& seat<rowC.length) {
                        System.out.println("Enter seat number");
                        seat = scanner.nextInt() - 1;
                        if(seat> rowA.length){
                            System.out.println("Invalid seat number!");
                            if (exit_loop()) {
                                return;
                            }
                        }else {
                            if (rowC[seat] == 1) {
                                cancelAndAdjust(seat, rowC, row);
                                break;
                            } else {
                                System.out.println("Invalid seat number!");
                                if (exit_loop()) {
                                    return;
                                }
                            }
                        }
                    } else if (Objects.equals(row, "D")&& seat<rowD.length) {
                        System.out.println("Enter seat number");
                        seat = scanner.nextInt() - 1;
                        if (seat > rowA.length) {
                            System.out.println("Invalid seat number!");
                            if (exit_loop()) {
                                return;
                            }
                        } else {
                            if (rowD[seat] == 1) {
                                cancelAndAdjust(seat, rowD, row);
                                break;
                            } else {
                                System.out.println("Invalid seat number!");
                                if (exit_loop()) {
                                    return;
                                }
                            }
                        }
                    }

                }catch (Exception ex){
                    System.out.println("Enter a number");
                }
            }else{
                System.out.println("Enter A,B,C,D");
            }
        }
    }
    static void cancelAndAdjust(int seat, int[] array, String row) { // this method use to remove cancelled seat from
        array[seat] = 0;                                             // ticketsArray and decrease its length by one
        Ticket[] temp = new Ticket[nextTicket - 1];
        int j=0;

        for (int i = 0; i < nextTicket; i++) { //temporarily copying ticketsArray and use temp array to extend and add
            if (!Objects.equals(ticketsArray[i].getRow(), row) || ticketsArray[i].getSeat() != seat) {
                temp[j++] = ticketsArray[i];
            }
        }

        File cancelText=new File("ticket "+row+" "+(seat+1)+".txt"); //removing cancelled seat ticket.txt file
        if (cancelText.exists()) {
            boolean isDeleted = cancelText.delete();
            if (isDeleted) {
                System.out.println("Ticket deleted successfully.");
            }
        }

        nextTicket--;  //decrease the length of ticketsArray by one
        ticketsArray=temp;
        System.out.println("seat number "+(seat+1)+" in row "+row+" cancelled");
    }
    static void find_first_available() { //method for check 1st available seat
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length; j++) {
                if (rows[i][j] == 0) {
                    if (rows[i] == rowA) {
                        System.out.println("The 1st available seat is seat number " + (j + 1) + " in Row A");
                        return;
                    } else if (rows[i] == rowB) {
                        System.out.println("The 1st available seat is seat number " + (j + 1) + " in Row B");
                        return;
                    } else if (rows[i] == rowC) {
                        System.out.println("The 1st available seat is seat number " + (j + 1) + " in Row C");
                        return;
                    } else if (rows[i] == rowD) {
                        System.out.println("The 1st available seat is seat number " + (j + 1) + " in Row D");
                        return;
                    }
                }
            }
        }
    }
    static void show_seating_plan() { //method for show visual representation of the plane's seats
        System.out.println(" ");
        System.out.println("(X - Booked Seats || O - Available Seats)");
        System.out.println();
        for (int j = 0; j < rows.length; j++) {
            if (rows[j] == rowC) {
                System.out.println(" ");
            }

            for (int i = 0; i < rows[j].length; i++) {
                if (rows[j][i] == 0) {
                    System.out.print(" O");
                } else {
                    System.out.print(" X");
                }
            }
            System.out.println();
        }
    }
    static Person get_details() { //passing values to a new person class' object
        System.out.println("Please enter your personal details here \n");
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your name :");
        String name = scanner.nextLine();

        System.out.println("Enter your surname :");
        String surName = scanner.nextLine();

        System.out.println("Enter your email :");
        String email = scanner.nextLine();

        return new Person(name, surName, email);
    }
    static void set_ticket(String row, int seat, double price, Person person) { //add a new ticket to the Ticket class
        Ticket ticket = new Ticket(row, seat, price, person);                   // array and extend it by one
        Ticket[] temp = new Ticket[nextTicket + 1];

        System.arraycopy(ticketsArray, 0, temp, 0, ticketsArray.length);
        save(ticket);
        temp[(nextTicket)] = ticket;
        ticketsArray = temp;
        nextTicket++;

        System.out.println("Seat number "+(seat+1)+",row "+row+" booked by "+person.getName()+" "+person.getSurName()+".");
        System.out.println("Thank You!");
    }
    static void print_tickets_info() { //display booked tickets' details (row letter and seat)
        double price = 0;
        System.out.println(" ");
        System.out.println("        Tickets");
        for (int i = 0; i < ticketsArray.length; i++) {
            if (ticketsArray[i] == null) {
                break;
            }
            System.out.println("            Ticket" + (i + 1) + " :" + ticketsArray[i].getRow() + (ticketsArray[i].getSeat()+1));
            System.out.println("             Customer name :"+ticketsArray[i].getPerson().getName()+" "+ticketsArray[i].getPerson().getSurName());
            System.out.println("             Email :"+ticketsArray[i].getPerson().getEmail());
            System.out.println();
            price = price + ticketsArray[i].getPrice();
        }
        System.out.println();
        System.out.println("Total ticket price is : £" + price);
    }
    static void search_ticket() { //method for search a ticket using its row and seat and check its availability
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter row(A,B,C,D) :");
        int rowDetector=4;
        String row = scanner.nextLine().toUpperCase();

        if (Objects.equals(row, "A") ||
            Objects.equals(row, "B") ||
            Objects.equals(row, "C") ||
            Objects.equals(row, "D")) {
            if(row.equals("A")){
                rowDetector=0;
            } else if (row.equals("B")) {
                rowDetector=1;
            } else if (row.equals("C")) {
                rowDetector=2;
            } else if (row.equals("D")) {
                rowDetector=3;
            }

            try {
                System.out.println("Enter seat number :");
                int seat = scanner.nextInt()-1;

                if ((row.equals("A") && seat < rowA.length)||
                    (row.equals("B") && seat < rowB.length)||
                    (row.equals("C") && seat < rowC.length)||
                    (row.equals("D") && seat < rowD.length)){
                    System.out.println(" ");

                    if (nextTicket==0) {
                        System.out.println("This seat is available!");
                        System.out.println("(You haven't booked any seat yet!)");
                        return;

                    }else {
                        for (int i = 0; i < nextTicket; i++) {
                            if (Objects.equals(ticketsArray[i].getRow(), row)
                                    && ticketsArray[i].getSeat() == seat
                                    && rows[rowDetector][seat] == 1) {
                                System.out.println("    Ticket Details    ");
                                ticketsArray[i].printTicket();
                                return;
                            }
                        }
                    }
                    if(ticketsArray[nextTicket-1].getSeat()!=seat
                            || !Objects.equals(ticketsArray[nextTicket - 1].getRow(), row)
                            || rows[rowDetector][seat]==0) {
                        System.out.println("This seat is available!");
                    }
                } else {
                    System.out.println("Invalid Seat!");

                    if(exit_loop()){
                        return;
                    }
                    search_ticket();
                }
            }catch (Exception ex){
                System.out.println("Enter a number");
                search_ticket(); //this method is not going with a loop,this is the reason for calling to method
            }                    // inside its
        }else{
            System.out.println("Invalid row!");
            search_ticket();
        }
    }
    static int set_price(int seat){  //method for using set price for booked seat
        int price;
        if (seat < 5) {
            price = 200;
        } else if (seat < 9) {
            price = 150;
        } else {
            price = 180;
        }
        return price;
    }
    static void save(Ticket ticket){  //method use to create .txt ticket file for each ticket including its details
        File file=new File("ticket "+ticket.getRow()+" "+(ticket.getSeat()+1)+".txt");

        try {
            FileWriter printTicket = new FileWriter(file);
            printTicket.write("Name:        " + ticket.getPerson().getName() + "\n");
            printTicket.write("Surname:     " + ticket.getPerson().getSurName() + "\n");
            printTicket.write("Email:       " + ticket.getPerson().getEmail() + "\n");
            printTicket.write("Seat:        " + (ticket.getSeat()+1) + "\n");
            printTicket.write("Row:         " + ticket.getRow() + "\n");
            printTicket.write("Price:       " + ticket.getPrice() + "\n");
            printTicket.write(" \n");
            printTicket.write("Date :"+ LocalDate.now()+"/ time :"+ LocalTime.now());
            printTicket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) { //This is the main method
        System.out.println();

        System.out.print("""
                         Welcome to the Plane Management application"
                         *****************************
                         *           MENU            *
                         *****************************
                         """);

        boolean exitOrNot=true;
        while (exitOrNot) {
            System.out.println();
            System.out.print("""
                   1. Buy a seat
                   2. Cancel a seat
                   3. Find first available seat
                   4. Show seating plan
                   5. Print tickets information and total sales
                   6. Search ticket
                   0. Quit
                   **********************************************
                   Please select an option:
                   """); //list of options

            Scanner scanner=new Scanner(System.in);
            try {
                int option = scanner.nextInt(); //switch-case to calling each method according to user's input
                switch (option) {
                    case 0:
                        exitOrNot=false;
                        break;
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception ex) {
                System.out.println("Enter a number!");
            }
        }
    }
}

