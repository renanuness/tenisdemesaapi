package br.edu.infnet.tenisdemesaapi.service;

import java.util.List;

import br.edu.infnet.tenisdemesaapi.dto.ReplyInviteDTO;
import br.edu.infnet.tenisdemesaapi.dto.ResultDTO;
import br.edu.infnet.tenisdemesaapi.model.Match;

public interface MatchService {
	void invitePlayerToMatch(Long userId, Long playerId, short bestOf);
	boolean cancelInvite(Long userId, Long matchId);
	List<Match> listInvites(Long userId);
	void replyInvite(ReplyInviteDTO reply);
	Match addGameResult(ResultDTO result);
}
