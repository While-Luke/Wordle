package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	String secretWord = "hello";

	@GetMapping("/")
	public ResponseEntity<String[]> checkWord(@RequestParam String guess) {
		if(guess.length() != 5) return ResponseEntity.badRequest().build();

		guess = guess.toLowerCase();

		return ResponseEntity.ok(computeResponse(secretWord, guess));
	}

	private String[] computeResponse(String answer, String guess){
		String[] response = new String[]{"N", "N", "N", "N", "N"};

		//Set Greens
		for(int i = 0; i < 5; i++){
			if(guess.charAt(i) == answer.charAt(i)) response[i] = "G";
		}

		//Set Yellows
		for(int i = 0; i < 5; i++){
			if(response[i] == "G") continue;
			for(int j = 0; j < 5; j++){
				if(i!= j && guess.charAt(i) == answer.charAt(j) && response[j] != "G") response[i] = "Y";
			}
		}

		return response;
	}

}