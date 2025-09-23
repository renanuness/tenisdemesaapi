package br.edu.infnet.tenisdemesaapi.business;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PointsCalculator {
	public List<ScoreRange> scoreRanges;
	public boolean expected;
	public ScoreRange scoreRangeSelected;
	public int pointsDifference;
	
	public PointsCalculator(boolean expected, int pointsDifference){
		this.expected = expected;
		this.pointsDifference = pointsDifference;
		
		if(expected) {
			scoreRanges = new ArrayList<ScoreRange>();
			loadJsonValues("scoreRangesExpected", scoreRanges);
		}else {
			scoreRanges = new ArrayList<ScoreRange>();
			loadJsonValues("scoreRangesUnexpected", scoreRanges);
		}
		
		for(ScoreRange sc : scoreRanges){
			if(sc.inRange(pointsDifference)) {
				scoreRangeSelected = sc;
				break;
			}
		}
		if(scoreRangeSelected == null) {
			throw new InvalidParameterException("Falha ao seleciona o score range");
		}
	}
	
	public Pair<Integer, Integer> calculatePoints() {
		
		return new Pair<Integer, Integer>(scoreRangeSelected.getwinnerMultiplier(), scoreRangeSelected.getLoserMulptiplier());
	}
	
	private void loadJsonValues(String file, List<ScoreRange> list) {
		String fileName = "target\\classes\\"+file +".json";
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "\\" + fileName;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        	var sb = new StringBuilder();
        	String line;
        	while((line = reader.readLine()) != null) {
        		sb.append(line);
        	}
        	ObjectMapper om = new ObjectMapper();
        	var objs = om.readValue(sb.toString(), new TypeReference<List<ScoreRange>>() {});
        	
        	for (ScoreRange scoreRange : objs) {
				list.add(scoreRange);
			}
			System.out.println("Conversão concluída");
        }catch(JsonMappingException ex) {
        	System.out.println(ex.getMessage());
        }catch(JsonProcessingException ex) {
        	System.out.println(ex.getMessage());
        }catch(FileNotFoundException ex) {
        	System.out.println(ex.getMessage());
        }catch(IOException ex) {
        	System.out.println(ex.getMessage());
        }
        
	}
}
