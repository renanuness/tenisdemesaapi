package br.edu.infnet.tenisdemesaapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.tenisdemesaapi.business.PointsCalculator;
import br.edu.infnet.tenisdemesaapi.dto.ReplyInviteDTO;
import br.edu.infnet.tenisdemesaapi.service.MatchService;
import br.edu.infnet.tenisdemesaapi.service.UserService;
import br.edu.infnet.tenisdemesaapi.dto.ResultDTO;
import br.edu.infnet.tenisdemesaapi.exception.BadRequestException;
import br.edu.infnet.tenisdemesaapi.exception.ForbiddenException;
import br.edu.infnet.tenisdemesaapi.exception.UserNotFoundException;
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
	public void invitePlayerToMatch(Long userId, Long playerId, short bestOf){
		var playerA = userService.getById(userId);
		if(playerA == null) {
			throw new UserNotFoundException();
		}
		var pendingInvitesSent = matchRepository.hasPendingInvitesSent(userId);
		
		if(pendingInvitesSent) {
			throw new BadRequestException("Você já está esperando um convite ser respondido.");
		}
		
		var playerB = userService.getById(playerId);
		
		if(playerB == null) {
			throw new UserNotFoundException();
		}
		
		var newMatch = new Match(playerA, playerB, bestOf);
		matchRepository.save(newMatch);
	}

	@Override
	public boolean cancelInvite(Long userId, Long matchId) {
		var match = matchRepository.getById(matchId);
		if(match == null) {
			throw new BadRequestException("Jogo inválido");
		}
		
		if(match.getPlayerAId() != userId) {
			throw new ForbiddenException("Usuário não autorizado");
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
			throw new BadRequestException("Jogo inválido");
		}
		if( match.getPlayerBId() != reply.userId) {
			throw new ForbiddenException();
		}
		
		if(match.getStatus() != MatchStatus.WAITING_ACCEPT) {
			throw new BadRequestException("Convite já foi respondido");
		}
		
		match.setStatus(MatchStatus.STARTED);
		matchRepository.save(match);
	}

	@Override
	public Match addGameResult(ResultDTO result) {
		var match = matchRepository.getReferenceById(result.matchId);
		
		if(match == null) {
			throw new BadRequestException("Jogo inválido");
		}
		
		if(match.getStatus() != MatchStatus.STARTED) {
			throw new BadRequestException("Não é possível adicionar resultados ao jogo atual.");
		}
		
		var game = new Game(match.getId(), result.playerAScore, result.playerBScore);
		
		match.addGame(game);
		matchRepository.save(match);
		
		if(match.getStatus() == MatchStatus.FINISHED) {
			updatePlayersPoints(match);
		}

		return match;
	}
	
	private void updatePlayersPoints(Match match) {
		if(match.getStatus() != MatchStatus.FINISHED) {
			throw new BadRequestException("Jogo não finalizado.");
		}
		
		var playerAid = match.getPlayerAId();
		var playerBid = match.getPlayerBId();
		
		var playerA = userService.getById(playerAid);
		var playerB = userService.getById(playerBid);
		
		if(playerA == null || playerB == null) {
			throw new UserNotFoundException();
		}
		var winner = match.getWinner();
		var loser = match.getWinner().getId() == playerAid ? playerB : playerA;
		var expectedWin = winner.getRankingPoints() > loser.getRankingPoints();
		
		var ptsDifference = Math.abs(winner.getRankingPoints() - loser.getRankingPoints());
		
		var pointsCalculator = new PointsCalculator(expectedWin, ptsDifference);
		var playerPoints = pointsCalculator.calculatePoints();
		winner.updateRankingPoints(playerPoints.getValue0());
		loser.updateRankingPoints(playerPoints.getValue1());
		
		userService.updateUserPoints(winner.getId(), winner.getRankingPoints());
		userService.updateUserPoints(loser.getId(), loser.getRankingPoints());
	}
}
