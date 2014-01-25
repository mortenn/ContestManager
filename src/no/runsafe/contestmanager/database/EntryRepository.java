package no.runsafe.contestmanager.database;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;

import java.util.List;

public class EntryRepository extends Repository
{
	public boolean addEntry(String world)
	{
		return database.execute("INSERT INTO contest_entries (world) VALUES (?)", world);
	}

	public boolean removeEntry(String world)
	{
		return database.execute("DELETE FROM contest_entries WHERE world=?", world);
	}

	public List<IWorld> getEntries()
	{
		return database.queryWorlds("SELECT world FROM contest_entries");
	}

	public List<String> getEntryNames()
	{
		return database.queryStrings("SELECT world FROM contest_entries");
	}

	@Override
	public String getTableName()
	{
		return "contest_entries";
	}

	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate schema = new SchemaUpdate();
		schema.addQueries(
			"CREATE TABLE contest_entries (" +
				"`world` VARCHAR(255) NOT NULL," +
				"PRIMARY KEY (`world`)" +
				")"
		);
		return schema;
	}
}
