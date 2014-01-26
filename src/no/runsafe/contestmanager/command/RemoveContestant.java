package no.runsafe.contestmanager.command;

import no.runsafe.contestmanager.database.ContestantRepository;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.AsyncCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.PlayerListArgument;
import no.runsafe.framework.api.player.IPlayer;

import java.util.List;

public class RemoveContestant extends AsyncCommand
{
	public RemoveContestant(IScheduler scheduler, ContestantRepository contestants)
	{
		super("remove", "Remove a contestant from their entry", "contestant.remove", scheduler, new PlayerListArgument(true));
		this.contestants = contestants;
	}

	@Override
	public String OnAsyncExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		List<IPlayer> players = parameters.getPlayers("players");
		if (players == null)
			return null;
		int success = 0;
		for (IPlayer player : players)
			if (contestants.setWorld(player.getName(), null))
				success++;

		return String.format("Removed %d players from the contest.", success);
	}

	private final ContestantRepository contestants;
}
