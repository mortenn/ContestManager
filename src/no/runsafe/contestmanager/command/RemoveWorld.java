package no.runsafe.contestmanager.command;

import no.runsafe.contestmanager.database.EntryRepository;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.AsyncCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.WorldArgument;

public class RemoveWorld extends AsyncCommand
{
	public RemoveWorld(IScheduler scheduler, EntryRepository entries)
	{
		super("remove", "Removes a world from the contest.", "contest.remove", scheduler, new WorldArgument(true));
		this.entries = entries;
	}

	@Override
	public String OnAsyncExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String world = parameters.get("world");
		return entries.removeEntry(world)
			? "The world " + world + " has been removed."
			: "Could not remove the world " + world + "!";
	}

	private EntryRepository entries;
}
