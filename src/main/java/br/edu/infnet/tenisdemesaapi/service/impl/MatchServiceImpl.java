package br.edu.infnet.tenisdemesaapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.tenisdemesaapi.dto.ReplyInviteDTO;
import br.edu.infnet.tenisdemesaapi.service.MatchService;
import br.edu.infnet.tenisdemesaapi.service.UserService;
import br.edu.infnet.tenisdemesaapi.dto.ResultDTO;
import br.edu.infnet.tenisdemesaapi.model.Game;
import br.edu.infnet.tenisdemesaapi.model.Match;
import br.edu.infnet.tenisdemesaapi.model.MatchStatus;
import br.edu.infnet.tenisdemesaapi.repository.MatchRepository;

@Service
public class MatchServiceImpl implements MatchService {

	private final MatchRepository matchRepository;
	private final UserService userService;
	
	public MatchServiceImpl(MatchRepository matchRepository, UserService userService) {
		this.userService = userService;
		this.matchRepository = matchRepository;
		
	}
	
	@Override
	public void invitePlayerToMatch(Long userId, Long playerId, short bestOf) {
		// Check if playerA has a game Started, if so, match cant be started
		var playerA = userService.getById(userId);
		if(playerA == null) {
			return;
		}
		var pendingInvitesSent = matchRepository.hasPendingInvitesSent(userId);
		
		if(pendingInvitesSent) {
			// TODO: Generate error
			return;
		}
		
		var playerB = userService.getById(playerId);
		
		if(playerA == null || playerB == null) {
			// TODO: Add some error
			return;
		}
		
		var newMatch = new Match(playerA, playerB, bestOf);
		matchRepository.save(newMatch);
	}

	@Override
	public boolean cancelInvite(Long userId, Long matchId) {
		var match = matchRepository.getById(matchId);
		if(match == null) {
			// TODO: Generate error
			return false;
		}
		
		if(match.getPlayerAId() != userId) {
			// TODO: Generate error
			return false;
		}
		
		matchRepository.delete(match);
		return true;
	}
	@Override
	public List<Match> listInvites(Long userId) {
		var invites = matchRepository.getPendingInvitesReceived(userId);
		
		return invites;
	}

	@Override
	public void replyInvite(ReplyInviteDTO reply) {
		var match = matchRepository.getReferenceById(reply.matchId);
		
		if (match == null) {
			// TODO: Generate error 
			return;
		}
		if( match.getPlayerBId() != reply.userId) {
			// TODO: Generate error
			return;
		}
		
		if(match.getStatus() != MatchStatus.WAITING_ACCEPT) {
			// TODO: Generate error
			return;
		}
		
		match.setStatus(MatchStatus.STARTED);
		matchRepository.save(match);
	}

	@Override
	public Match addGameResult(ResultDTO result) {
		// TODO Auto-generated method stub
		var match = matchRepository.getReferenceById(result.matchId);
		
		if(match == null) {
			// TODO: Generate error
			return match;
		}
		
		if(match.getStatus() != MatchStatus.STARTED) {
			// TODO: Generate error
			return match;
		}
		
		var game = new Game(match.getId(), result.playerAScore, result.playerBScore);
		
		match.addGame(game);
		match.checkEndMatch();
		matchRepository.save(match);
		
		return match;
	}
	
}
