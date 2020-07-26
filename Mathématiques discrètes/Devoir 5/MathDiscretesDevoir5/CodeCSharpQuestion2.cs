using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp6
{
    class Program
    {
        private static int possiblesCombinations2a = 0;
        private static int possiblesCombinations2b = 0;
        private static int possiblesCombinations2c = 0;

        static void Main(string[] args)
        {
            Question2a(1, 83);
            Console.WriteLine(possiblesCombinations2a);

            Question2b(1, 83);
            Console.WriteLine(possiblesCombinations2b);

            Question2c(1, 83);
            Console.WriteLine(possiblesCombinations2c);


            Console.ReadLine();

        }


        private static void Question2a(int numTable, int numPersonnesRestantsAAssoir)
        {
            for (int i = numTable; i <= 6; i++)
            {
                for (int j = 1; j <= numPersonnesRestantsAAssoir; j++)
                {
                    var numPersonnesAssisDansLaTable = j;
                    var restantsPerssonesAAssoir = numPersonnesRestantsAAssoir - numPersonnesAssisDansLaTable;
                    var tableSuivant = i + 1;
                    Question2a(tableSuivant, restantsPerssonesAAssoir);

                    if (restantsPerssonesAAssoir == 0)
                    {
                        possiblesCombinations2a++;
                    }
                }
            }
        }

        private static void Question2b(int numTable, int numPersonnesRestantsAAssoir)
        {
            for (int i = numTable; i <= 6; i++)
            {
                for (int j = 1; j <= numPersonnesRestantsAAssoir; j++)
                {
                    if (j >= 10)
                    { 
                        var numPersonnesAssisDansLaTable = j;
                        var restantsPerssonesAAssoir = numPersonnesRestantsAAssoir - numPersonnesAssisDansLaTable;
                        var tableSuivant = i + 1;
                        Question2b(tableSuivant, restantsPerssonesAAssoir);

                        if (restantsPerssonesAAssoir == 0)
                        {
                            possiblesCombinations2b++;
                        }
                    }
                }
            }
        }


        private static void Question2c(int numTable, int numPersonnesRestantsAAssoir)
        {
            for (int i = numTable; i <= 6; i++)
            {
                for (int j = 1; j <= numPersonnesRestantsAAssoir; j++)
                {
                    if (j >= 10 && j<= 16)
                    {
                        var numPersonnesAssisDansLaTable = j;
                        var restantsPerssonesAAssoir = numPersonnesRestantsAAssoir - numPersonnesAssisDansLaTable;
                        var tableSuivant = i + 1;
                        Question2c(tableSuivant, restantsPerssonesAAssoir);

                        if (restantsPerssonesAAssoir == 0)
                        {
                            possiblesCombinations2c++;
                        }
                    }
                }
            }
        }
      
    }
}
