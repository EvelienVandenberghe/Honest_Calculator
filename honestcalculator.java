package honestcalculator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String msg_0 = "Enter an equation";
        String msg_1 = "Do you even know what numbers are? Stay focused!";
        String msg_2 = "Yes ... an interesting math operation. You've slept through all classes, haven't you?";
        String msg_3 = "Yeah... division by zero. Smart move...";
        String msg_4 = "Do you want to store the result? (y / n):";
        String msg_5 = "Do you want to continue calculations? (y / n):";
        String msg_6 = " ... lazy";
        String msg_7 = " ... very lazy";
        String msg_8 = " ... very, very lazy";
        String msg_9 = "You are";
        String msg_10 = "Are you sure? It is only one digit! (y / n)";
        String msg_11 = "Don't be silly! It's just one number! Add to the memory? (y / n)";
        String msg_12 = "Last chance! Do you really want to embarrass yourself? (y / n)";
        
        double memory = 0;
        
        while (true) {
            System.out.println(msg_0);
            String calc = scanner.nextLine();
            
            String[] parts = calc.split(" ");
            
            if (parts.length != 3) {
                continue;
            }
            
            String xStr = parts[0];
            String oper = parts[1];
            String yStr = parts[2];
            
            if (xStr.equals("M")) {
                xStr = String.valueOf(memory);
            }
            if (yStr.equals("M")) {
                yStr = String.valueOf(memory);
            }
            
            boolean xIsNumber = isNumber(xStr);           // Try to parse x and y as numbers
            boolean yIsNumber = isNumber(yStr);
            
            if (!xIsNumber || !yIsNumber) {
                System.out.println(msg_1);
                continue;
            }
            
            if (!isValidOperation(oper)) {
                System.out.println(msg_2);
                continue;
            }
            
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            
            checkLaziness(x, y, oper, msg_6, msg_7, msg_8, msg_9);
            
            if (oper.equals("/") && y == 0) {
                System.out.println(msg_3);
                continue;
            }
            
            double result = calculate(x, y, oper);
            
            if (result == Math.floor(result) && !Double.isInfinite(result)) {     // Avoiding overflow and infinity edge cases
                System.out.println((long) result + ".0");
            } else {
                System.out.println(result);
            }
            
            System.out.println(msg_4);
            String storeAnswer = scanner.nextLine();
            
            if (storeAnswer.equals("y")) {
                if (isOneDigit(result)) {
                    System.out.println(msg_10);
                    String confirm1 = scanner.nextLine();
                    
                    if (confirm1.equals("y")) {
                        System.out.println(msg_11);
                        String confirm2 = scanner.nextLine();
                        
                        if (confirm2.equals("y")) {
                            System.out.println(msg_12);
                            String confirm3 = scanner.nextLine();
                            
                            if (confirm3.equals("y")) {
                                memory = result;
                            }
                        }
                    }
                } else {
                    memory = result;
                }
            }
            
            System.out.println(msg_5);
            String continueAnswer = scanner.nextLine();
            if (continueAnswer.equals("n")) {
                break;
            }
        }
        
        scanner.close();
    }
    
    private static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static boolean isValidOperation(String oper) {
        return oper.equals("+") || oper.equals("-") || 
               oper.equals("*") || oper.equals("/");
    }
    
    private static double calculate(double x, double y, String oper) {
        switch (oper) {
            case "+":
                return x + y;
            case "-":
                return x - y;
            case "*":
                return x * y;
            case "/":
                return x / y;
            default:
                return 0;
        }
    }
    
    private static boolean isOneDigit(double num) {
        long rounded = Math.round(num);            // Round the number to handle floating point precision
                
        if (Math.abs(num - rounded) > 0.0001) {               // Check if rounding changed the value significantly (not an integer)
            return false;
        }
                
        return rounded >= 0 && rounded <= 9;                    // Check if it's a single digit (0-9)
    }
    
    private static void checkLaziness(double x, double y, String oper, 
                                      String msg_6, String msg_7, String msg_8, String msg_9) {
        StringBuilder lazyMessage = new StringBuilder();
        
        if (isOneDigit(x) && isOneDigit(y)) {
            lazyMessage.append(msg_6);
        }
        
        if (x == 1 || y == 1) { if (oper.equals("*") || oper.equals("/")) { 
            lazyMessage.append(msg_7); } 
        }
        
        if (x == 0 || y == 0) { if (oper.equals("*") || oper.equals("+")) { 
            lazyMessage.append(msg_8); }
        }
        
        if (lazyMessage.length() > 0) {
            System.out.println(msg_9 + lazyMessage.toString());
        }
    }
}



