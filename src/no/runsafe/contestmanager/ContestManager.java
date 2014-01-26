package no.runsafe.contestmanager;

import no.runsafe.contestmanager.command.AddContestant;
import no.runsafe.contestmanager.command.AddWorld;
import no.runsafe.contestmanager.command.RemoveContestant;
import no.runsafe.contestmanager.command.RemoveWorld;
import no.runsafe.contestmanager.database.ContestantRepository;
import no.runsafe.contestmanager.database.EntryRepository;
import no.runsafe.contestmanager.event.PlayerEvents;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Database;
import no.runsafe.framework.features.Events;

public class ContestManager extends RunsafePlugin
{
	@Override
	protected void pluginSetup()
	{
		// Framework features
		addComponent(Commands.class);
		addComponent(Database.class);
		addComponent(Events.class);

		// Plugin components
		addComponent(ContestantRepository.class);
		addComponent(EntryRepository.class);

		// Event handlers
		addComponent(PlayerEvents.class);

		// Commands
		Command contest = new Command("contest", "Creative contest management tools", null);

		Command entry = new Command("entry", "Manage contest entries", null);
		entry.addSubCommand(getInstance(AddWorld.class));
		entry.addSubCommand(getInstance(RemoveWorld.class));
		contest.addSubCommand(entry);

		Command contestant = new Command("contestant", "Manage contestants", null);
		contestant.addSubCommand(getInstance(AddContestant.class));
		contestant.addSubCommand(getInstance(RemoveContestant.class));
		contest.addSubCommand(contestant);

		addComponent(contest);
	}
}
