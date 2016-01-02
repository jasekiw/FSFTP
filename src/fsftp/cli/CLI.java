package fsftp.cli;

import fsftp.FSFTP;
import fsftp.cli.parameters.ParameterParser;

import java.util.Scanner;

/**
 * Created by Jason on 12/5/2015.
 */
public class CLI {
    FSFTP fsftp;
    Scanner input;
    public static void main(String[] args) {
        new CLI().run(args);
    }

    public void run(String[] args)
    {
        input = new Scanner(System.in);
        ParameterParser parser = new ParameterParser();
        fsftp = new FSFTP();

        parser.parse(args);
        if(parser.haveAllParameters())
            startSession(parser);
        else
        {
            promptforRemaining(parser);
            startSession(parser);
        }


    }

    void promptforRemaining(ParameterParser parser)
    {

        if(parser.host == null)
        {
            System.out.println("What is the host to connect to?");
            parser.host = input.nextLine();
        }
        if(parser.user == null)
        {
            System.out.println("What is the user to connect with?");
            parser.user = input.nextLine();
        }
        if(parser.password == null)
        {
            System.out.println("What is the password to connect with?");
            parser.password = input.nextLine();
        }

    }

    void startSession(ParameterParser parser) {

        fsftp.setServer(parser.host,parser.user, parser.password, parser.port);
        boolean stopped =false;
        String inputLine = "";
        while(!stopped)
        {
            inputLine = input.nextLine();
            System.out.println(processCommand(inputLine));
        }

    }

    String processCommand(String input)
    {
        String[] args = input.split("\\s+");
        String command = args[0];
        if(command.equals("ls"))
            return fsftp.directoryListing(args[1]);
        if(command.equals("exit"))
            System.exit(0);

        return "";
    }




}
