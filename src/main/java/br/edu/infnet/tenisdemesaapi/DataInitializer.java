package br.edu.infnet.tenisdemesaapi;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.edu.infnet.tenisdemesaapi.model.User;
import br.edu.infnet.tenisdemesaapi.repository.UserRepository;

@Component
public class DataInitializer implements ApplicationRunner{
	@Autowired
    private UserRepository userRepository;
    
	@Override
    public void run(ApplicationArguments args) throws Exception {
        String fileName = "target\\classes\\data.txt";
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "\\" + fileName;
      
        System.out.println("=== APPLICATION RUNNER INICIADO ===");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.replaceAll("'", "").split(",");
                
                if (parts.length == 4) {
                    String name = parts[0].trim();
                    String email = parts[1].trim();
                    String password = parts[2].trim();
                    int rankingPoints = Integer.parseInt(parts[3].trim());
                    
                    if (userRepository.findByEmail(email) == null) {
                        User user = new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setRankingPoints(rankingPoints);
                        
                        userRepository.save(user);
                        System.out.println("Usuário adicionado: " + email);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo ou usuário já existe: " + e.getMessage());
        }
    }
}
