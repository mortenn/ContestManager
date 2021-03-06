package no.runsafe.contestmanager.event;

import no.runsafe.contestmanager.database.ContestantRepository;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.player.IPlayerJoinEvent;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerJoinEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class PlayerEvents implements IPlayerJoinEvent, IPlayerTeleportEvent, IPlayerRightClick
{
	public PlayerEvents(ContestantRepository contestants, IScheduler scheduler)
	{
		this.contestants = contestants;
		this.scheduler = scheduler;
	}

	@Override
	public void OnPlayerJoinEvent(RunsafePlayerJoinEvent event)
	{
		final IPlayer player = event.getPlayer();
		if (!isMemberOfWorld(player, player.getWorld()))
		{
			final IWorld entry = contestants.getWorldOf(player);
			if (entry == null)
				scheduler.startSyncTask(new Runnable()
				{
					@Override
					public void run()
					{
						player.performCommand("warp lobby");
					}
				},
					0
				);
			else
				scheduler.startSyncTask(
					new Runnable()
					{
						@Override
						public void run()
						{
							player.performCommand("mvtp " + entry.getName());

						}
					},
					0
				);
		}
	}

	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, IBlock targetBlock)
	{
		return targetBlock == null || isMemberOfWorld(player, targetBlock.getWorld());
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		if (event.getPlayer().hasPermission("contest.admin"))
			return;
		if (event.getTo().getWorld().getName().equals("world"))
			return;

		if (!isMemberOfWorld(event.getPlayer(), event.getTo().getWorld()))
		{
			event.cancel();
			event.getPlayer().sendColouredMessage("Du har ikke tilgang til denne verdenen.");
		}
	}

	private boolean isMemberOfWorld(IPlayer player, IWorld world)
	{
		if (world.getName().equals("world") || world.getName().equals("world_nether") || world.getName().equals("world_the_end"))
			return true;

		return contestants.getWorldMemberNames(world.getName()).contains(player.getName());
	}

	private final ContestantRepository contestants;
	private final IScheduler scheduler;
}
