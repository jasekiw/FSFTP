package fsftp.cli.parameters;

/**
 * Created by Jason on 12/5/2015.
 */
public class ParameterParser {
    public String host = null;
    public String password = null;
    public String port = null;
    public String user = null;
    String[] parameters;
    public void parse(String[] args)
    {
        this.parameters = args;
        if(args.length == 0)
        {
            port = "22";
            return;
        }

        if(args.length == 1)
        {
            host = args[0];
            port = "22";
            return;
        }

       for(int i =0; i < args.length; i++)
        {
            if(args[i].equals("-p"))
            {
                if(within(i))
                {
                    port = args[i + 1];
                    i++; // skips the one that was used
                }
                else
                    print("No port given");
            }
            else if(args[i].equals("-pw"))
            {
                if(within(i))
                {
                    password = args[i + 1];
                    i++; // skips the one that was used
                }
                else
                    print("No password given");
            }
            else if(args[i].equals("-u"))
            {
                if(within(i))
                {
                    user = args[i + 1];
                    i++; // skips the one that was used
                }
                else
                    print("No user given");
            }
            else
            {
                host = args[i];
            }
            i++;
        }
        if(port == null)
            port = "22";
    }

    private void print(String output)
    {
        System.out.println(output);
    }
    private boolean within(int index)
    {
        return parameters.length > (index + 1);
    }
    public boolean haveAllParameters()
    {
        return (user != null && password != null && host != null && port != null);
    }
}
