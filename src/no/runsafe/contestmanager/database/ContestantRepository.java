package no.runsafe.contestmanager.database;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.player.IPlayer;

import java.util.List;

public class ContestantRepository extends Repository
{
	public List<IPlayer> getWorldMembers(String world)
	{
		return database.queryPlayers("SELECT name FROM contest_contestants WHERE world=?", world);
	}

	public List<String> getWorldMemberNames(String world)
	{
		return database.queryStrings("SELECT name FROM contest_contestants WHERE world=?", world);
	}

	public IWorld getWorldOf(IPlayer player)
	{
		return database.queryWorld("SELECT world FROM contest_contestants WHERE name=?", player.getName());
	}

	public boolean setWorld(String name, String world)
	{
		return database.execute(
			"INSERT INTO contest_contestants (name, world) VALUES (?, ?)" +
				" ON DUPLICATE KEY UPDATE world=VALUES(world)",
			name, world
		);
	}

	@Override
	public String getTableName()
	{
		return "contest_contestants";
	}

	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate schema = new SchemaUpdate();
		schema.addQueries(
			"CREATE TABLE contest_contestants (" +
				"`name` VARCHAR(20) NOT NULL," +
				"`world` VARCHAR(255)," +
				"PRIMARY KEY(`name`)" +
				")"
		);
		return null;
	}
}
