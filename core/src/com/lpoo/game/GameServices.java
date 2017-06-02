package com.lpoo.game;

public interface GameServices {

	public void signIn();
	public void signOut();

	public boolean isSignedIn();

	public void submitScore(int score);
	public void unlockAchievements(String achievementID);

	//gets the scores/achievements and displays them through google's default widget
	public void showScores();
	public void showAchievements();	
}