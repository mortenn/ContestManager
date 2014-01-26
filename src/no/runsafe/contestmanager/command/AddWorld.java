package no.runsafe.contestmanager.command;

import no.runsafe.contestmanager.database.EntryRepository;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.AsyncCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.WorldArgument;

public class AddWorld extends AsyncCommand
{
	public AddWorld(IScheduler scheduler, EntryRepository entries)
	{
		super("add", "Adds a world to the contest", "contest.add", scheduler, new WorldArgument(true));
		this.entries = entries;
	}

	@Override
	public String OnAsyncExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String world = parameters.get("world");
		return entries.addEntry(world)
			? "The world " + world + " has been added."
			: "Could not add the world " + world + "!";
	}

	private final EntryRepository entries;
}
