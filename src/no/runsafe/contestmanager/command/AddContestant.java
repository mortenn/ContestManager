package no.runsafe.contestmanager.command;

import no.runsafe.contestmanager.database.ContestantRepository;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.AsyncCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.PlayerListArgument;
import no.runsafe.framework.api.command.argument.WorldArgument;
import no.runsafe.framework.api.player.IPlayer;

import java.util.List;

public class AddContestant extends AsyncCommand
{
	protected AddContestant(IScheduler scheduler, ContestantRepository contestants)
	{
		super("add", "Add a contestant to an entry", "contestant.add", scheduler, new WorldArgument(true), new PlayerListArgument(true));
		this.contestants = contestants;
	}

	@Override
	public String OnAsyncExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		List<IPlayer> players = parameters.getPlayers("players");
		if (players == null)
			return null;
		String world = parameters.get("world");
		int success = 0;
		for (IPlayer player : players)
			if (contestants.setWorld(player.getName(), world))
				success++;

		return String.format("Added %d players to the world %s.", success, world);
	}

	private final ContestantRepository contestants;
}
