package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;

public class ETItZfzG {
    public static void main(String[] args) {
        try {
            // Connect to RMI Registry
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            
            // Lookup remote object
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            
            // Student information
            String studentCode = "B22DCVT445";
            String qCode = "ETItZfzG";
            
            // a. Request string from server
            String inputString = service.requestCharacter(studentCode, qCode);
            System.out.println("Input string: " + inputString);
            
            // b. Count character frequency
            String result = countCharacterFrequency(inputString);
            System.out.println("Result: " + result);
            
            // c. Submit result to server
            service.submitCharacter(studentCode, qCode, result);
            System.out.println("Submitted result to server");
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
    
    private static String countCharacterFrequency(String input) {
        // Use LinkedHashMap to preserve insertion order
        Map<Character, Integer> frequencyMap = new LinkedHashMap<>();
        
        // Count frequency of each character
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        
        // Build result string in format <Character><Count>
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            result.append(entry.getKey()).append(entry.getValue());
        }
        
        return result.toString();
    }
}
