package UCSHelpdesk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class CLI {

    private InputStreamReader streamReader = new InputStreamReader(System.in);
    private BufferedReader input = new BufferedReader(streamReader);
    private Helpdesk helpdesk;
    private Receptionist loggedInReceptionist;

    public CLI(Helpdesk helpdesk) throws IOException {
        this.helpdesk = helpdesk;
        login();
    }

    private void login() throws IOException {
        System.out.println("Welcome to " + helpdesk.getHelpdeskName() + " Helpdesk" );
        System.out.println("------------------------------" );
        System.out.print("Enter User ID: ");
        String userID = input.readLine();
        System.out.print("Enter Password: ");
        String password = input.readLine();


       int userIDNumber = checkInt(userID);

       if(authenticateUser(userIDNumber, password))
       {
           loggedInReceptionist = helpdesk.getReceptionist(userIDNumber);

           ticketMenu();
       }
       else
       {
           System.out.println("Incorrect Credentials");
           login();
       }

    }

    private int checkInt(String number)
    {
        try
        {
            return Integer.parseInt(number);
        }
        catch (NumberFormatException ex)
        {
            return -1;
        }
    }

    private boolean authenticateUser(int userID, String password)
    {
        Receptionist receptionist = helpdesk.getReceptionist(userID);
        if (receptionist != null){

            if(receptionist.getPassword().equals(password))
            {
                return true;
            }
            else
            {
                return false; //Incorrect password
            }
        }
        return false;

    }

    private void ticketMenu() throws IOException {
        System.out.println("Ticket Menu");
        System.out.println("------------------------------" );
        System.out.println("1. Open Ticket" );
        System.out.println("2. Search and View Ticket" );
        System.out.println("3. Add Action" );
        System.out.println("------------------------------" );
        System.out.print("Enter choice: ");
        String choice = input.readLine();

        switch(choice)
        {
            case "1":
                createTicket();
                break;
            case "2":
                viewTickets();
                break;
            case "3":
                addAction();
                break;
            default:
                System.out.println("Not an option");
                ticketMenu();

        }

    }

    private void createTicket() throws IOException {
        System.out.print("Enter General Ticket Details: ");
        String details = input.readLine();
        helpdesk.addTicket(new GeneralTicket(loggedInReceptionist, details));
        ticketMenu();
    }

    private void viewTickets() throws IOException {
        System.out.print(helpdesk.getTickets());
        ticketMenu();
    }

    private void addAction()
    {


    }
}
