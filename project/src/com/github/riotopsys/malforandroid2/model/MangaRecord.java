package com.github.riotopsys.malforandroid2.model;

import java.util.LinkedList;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "manga")
public class MangaRecord {
	
	@DatabaseField(id = true)
	public int id;
	
	@DatabaseField
	public String title;
	
	@DatabaseField
	public int rank;
	
	@DatabaseField
	public String image_url;

	@DatabaseField
	public String type;
	
	@DatabaseField
	public String status;
	
	@DatabaseField
	public int volumes;
	
	@DatabaseField
	public int chapters;
	
	@DatabaseField(dataType=DataType.SERIALIZABLE)
	public LinkedList<String> genres;
	
	@DatabaseField
	public float members_score;
	
	@DatabaseField
	public int members_count;
	
	@DatabaseField
	public int popularity_rank;
	
	@DatabaseField
	public int favorited_count;
	
	@DatabaseField(dataType=DataType.SERIALIZABLE)
	public LinkedList<String> tags;
	
	@DatabaseField
	public String synopsis;
	
	@DatabaseField(dataType=DataType.SERIALIZABLE)
	public LinkedList<AnimeCrossReferance> anime_adaptations;
	
	@DatabaseField(dataType=DataType.SERIALIZABLE)
	public LinkedList<MangaCrossReferance> related_manga;
	
	@DatabaseField(dataType=DataType.SERIALIZABLE)
	public LinkedList<MangaCrossReferance> alternative_versions;
	
	@DatabaseField()
	public MangaReadStatus read_status;
	
	@DatabaseField()
	public int listed_manga_id;
	
	@DatabaseField()
	public int chapters_read;
	
	@DatabaseField()
	public int volumes_read;
	
	@DatabaseField()
	public int score;
	
}
