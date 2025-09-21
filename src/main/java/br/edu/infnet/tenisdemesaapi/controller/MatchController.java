package br.edu.infnet.tenisdemesaapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.infnet.tenisdemesaapi.dto.InviteDTO;
import br.edu.infnet.tenisdemesaapi.dto.ReplyInviteDTO;
import br.edu.infnet.tenisdemesaapi.dto.ResultDTO;
import br.edu.infnet.tenisdemesaapi.model.Match;
import br.edu.infnet.tenisdemesaapi.service.MatchService;

@Controller
@RequestMapping("/api/match")
public class MatchController {
	
	private final MatchService matchService;
	
	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}
	
	@PostMapping("/invitePlayer")
	public ResponseEntity<String> invitePlayer(@RequestHeader Long userId, @RequestBody InviteDTO model){
		matchService.invitePlayerToMatch(userId, model.playerId, model.bestOf);
		
		return ResponseEntity.ok().body("Convite enviado");
	}
	
	@DeleteMapping("/cancelInvite/{matchId}")
	public ResponseEntity<String> cancelInvite(@RequestHeader Long userId, @PathVariable Long matchId){
		var response = matchService.cancelInvite(userId, matchId);
			
		return ResponseEntity.ok().body("Convite cancelado com sucesso");
	}
	
	//List invites
	@GetMapping("/listInvites")
	public ResponseEntity<List<Match>> listInvites(@RequestHeader Long userId){
		var invites = matchService.listInvites(userId);
		
		return ResponseEntity.ok().body(invites);
	}
	
	//Accept/decline invite
	@PostMapping("/replyInvite")
	public ResponseEntity<String> replyInvite(@RequestHeader Long userId, @RequestBody ReplyInviteDTO reply){
		reply.userId = userId;
		matchService.replyInvite(reply);
		return ResponseEntity.ok().body("Resposta enviada");
	}
	
	//AddGameResult
	@PostMapping("addGameResult")
	public ResponseEntity<Match> addGameResult(@RequestHeader Long userId, @RequestBody Long matchId, @RequestBody short playerAScore, @RequestBody short playerBScore){
		var result = new ResultDTO();
		result.matchId = matchId;
		result.playerAScore = playerAScore;
		result.playerBScore = playerBScore;
		var match = matchService.addGameResult(result);
		return ResponseEntity.ok().body(match);
	}
}
