package taskone;

import java.util.Random;
import java.util.Scanner;
public class Main {

    private static int getAreasAmountForFlock(int flock_amount,int areas_amount){
        return (flock_amount+areas_amount-1)/flock_amount;
    }
    private static int getFlockStart(int flock_number, int areas_amount_for_flock){
        return flock_number*areas_amount_for_flock;
    }
    private static int getFlockEnd(int flock_number, int areas_amount_for_flock,int areas_amount){
        int end = (flock_number+1)*areas_amount_for_flock;
        return Math.min(end, areas_amount);
    }
    private static void FindingTheWinnie(int flock_number,int areas_amount){
        int winnie_location = new Random().nextInt(areas_amount);
        FlockSearch.location_of_winnie=winnie_location;
        FlockSearch[] searches = new FlockSearch[flock_number];
        System.out.println("Winnie is at the "+ winnie_location+ " area");
        int areas_amount_for_flock = getAreasAmountForFlock(flock_number,areas_amount);
        for(int i=0;i<flock_number;i++){
            searches[i] = new FlockSearch(getFlockStart(i,areas_amount_for_flock),getFlockEnd(i,areas_amount_for_flock,areas_amount),i+1);
        }
        for(int i=1;i<flock_number;i++){
            searches[i].fork();
        }
        searches[0].compute();
        for(int i=1;i<flock_number;i++){
            searches[i].join();
        }
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an amount of flocks:");
        int flock_number = scanner.nextInt();
        System.out.println("Enter an areas amount:");
        int areas_amount = scanner.nextInt();
        Main.FindingTheWinnie(flock_number,areas_amount);
    }

}
