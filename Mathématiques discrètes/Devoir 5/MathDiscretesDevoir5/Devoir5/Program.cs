using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Devoir5
{
    class Program
    {
        private static int possiblesCombinations2a = 0;
        private static int possiblesCombinations2b = 0;
        private static int possiblesCombinations2c = 0;
        private static int[] itemsQuestion2 = new int[6];
        
        static void Main(string[] args)
        {
            Question2a(1, 83);
            Console.WriteLine("2a) " + possiblesCombinations2a);

            itemsQuestion2 = new int[6];
            Question2b(1, 83);
            Console.WriteLine("2b) " + possiblesCombinations2b);

            itemsQuestion2 = new int[6];
            Question2c(1, 83);
            Console.WriteLine("2c) " +possiblesCombinations2c);            

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
            if (numTable > 6)
                return;

            InitArrayWithZeros(numTable - 1, itemsQuestion2);

            for (int j = 1; j <= numPersonnesRestantsAAssoir; j++)
            {
                if (j >= 12)
                {
                    var numPersonnesAssisDansLaTable = j;
                    var restantsPerssonesAAssoir = numPersonnesRestantsAAssoir - numPersonnesAssisDansLaTable;
                    var tableSuivant = numTable + 1;

                    itemsQuestion2[numTable - 1] = numPersonnesAssisDansLaTable;

                    Question2b(tableSuivant, restantsPerssonesAAssoir);                 
                    var tousTablesAvecPersonnes = tousItemsInArrayAreDifferentToZero(itemsQuestion2);

                    if (restantsPerssonesAAssoir == 0 && tousTablesAvecPersonnes)                            
                    {
                        possiblesCombinations2b++;
                    }
                }                    
            }
        }

     
        private static void Question2c(int numTable, int numPersonnesRestantsAAssoir)
        {
            if (numTable > 6)
                return;

            InitArrayWithZeros(numTable - 1, itemsQuestion2);

            for (int j = 1; j <= numPersonnesRestantsAAssoir; j++)
            {
                if (j >= 10 && j<=16)
                {
                    var numPersonnesAssisDansLaTable = j;
                    var restantsPerssonesAAssoir = numPersonnesRestantsAAssoir - numPersonnesAssisDansLaTable;
                    var tableSuivant = numTable + 1;

                    itemsQuestion2[numTable - 1] = numPersonnesAssisDansLaTable;

                    Question2c(tableSuivant, restantsPerssonesAAssoir);
                    var tousTablesAvecPersonnes = tousItemsInArrayAreDifferentToZero(itemsQuestion2);

                    if (restantsPerssonesAAssoir == 0 && tousTablesAvecPersonnes)
                    {
                        possiblesCombinations2c++;
                    }
            }
        }
    }


    private static bool tousItemsInArrayAreDifferentToZero(int[] array)
        {
            for (var i=0; i<array.Length; i++) {
                if (array[i] == 0)
                    return false;
            }
            return true;
        }

        private static void InitArrayWithZeros(int positionArray, int[] array)
        {
            for (var i = positionArray; i < array.Length; i++)
            {
                array[i] = 0; 
            }            
        }


        private static void printArray(int[]  items)
        {
            Console.WriteLine(string.Join(",", items));
        }
    }
}
